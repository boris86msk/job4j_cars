package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.BodyTypeService;
import ru.job4j.cars.service.CarBrandService;
import ru.job4j.cars.service.ParticipatesService;
import ru.job4j.cars.service.PostService;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PostControllerTest {
    public BodyTypeService bodyTypeService;
    public PostService postService;
    public ParticipatesService participatesService;
    public CarBrandService carBrandService;
    public PostController postController;
    public ConcurrentModel model;

    @BeforeEach
    public void initService() {
        model = mock(ConcurrentModel.class);
        bodyTypeService = mock(BodyTypeService.class);
        postService = mock(PostService.class);
        participatesService = mock(ParticipatesService.class);
        carBrandService = mock(CarBrandService.class);
        postController = new PostController(bodyTypeService, postService, participatesService, carBrandService);
    }

    @Test
    void wenGetCreationPage() {
        var view = postController.getCreatePage(model);
        verify(model).addAttribute(eq("bodyType"), anyList());
        verify(model).addAttribute(eq("carModel"), anyList());
        assertThat(view).isEqualTo("post/create");
    }

    @Test
    void wenGetPageForOnePost() {
        User user = new User();
        Post post = new Post();
        var postId = 1;
        when(postService.findById(any(Integer.class))).thenReturn(Optional.of(post));
        var view = postController.getOnePage(postId, model, user);
        verify(model).addAttribute(eq("user"), any(User.class));
        verify(model).addAttribute(eq("post"), any(Post.class));
        assertThat(view).isEqualTo("post/one");
    }

    @Test
    void wenSuccessfullySaveNewPost() throws IOException {
        Post post = mock(Post.class);
        MultipartFile myFile = mock(MultipartFile.class);
        User user = mock(User.class);
        when(postService.savePost(post, myFile, user)).thenReturn(Optional.of(post));
        var view = postController.saveNewPost(post, myFile, user, model);
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    void wenUnsuccessfullySaveNewPost() throws IOException {
        Post post = mock(Post.class);
        MultipartFile myFile = mock(MultipartFile.class);
        User user = mock(User.class);
        when(postService.savePost(post, myFile, user)).thenReturn(Optional.empty());
        var view = postController.saveNewPost(post, myFile, user, model);
        verify(model).addAttribute(eq("errorText"), any(String.class));
        assertThat(view).isEqualTo("errorPage");
    }

    @Test
    void wenSuccessfullyDeletePostById() throws IOException {
        var postId = 1;
        var fileId = 1;
        when(postService.deletePost(any(Integer.class), any(Integer.class))).thenReturn(true);
        var view = postController.deletePost(postId, fileId, model);
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    void wenUnsuccessfullyDeletePostById() throws IOException {
        var postId = 1;
        var fileId = 1;
        when(postService.deletePost(any(Integer.class), any(Integer.class))).thenReturn(false);
        var view = postController.deletePost(postId, fileId, model);
        verify(model).addAttribute(eq("errorText"), any(String.class));
        assertThat(view).isEqualTo("errorPage");
    }

    @Test
    void wenWeChangePostPrice() {
        Post post = new Post();
        post.setId(1);
        var priceHistory = mock(PriceHistory.class);
        when(postService.savePriceHistory(post)).thenReturn(Optional.of(priceHistory));
        var view = postController.changePrice(post);
        verify(postService).updateById(post.getId(), eq(anyInt()));
        assertThat(view).isEqualTo("redirect:/one/1");
    }

    @Test
    void wenAddPostToFavourites() {
        var postId = 1;
        var view = postController.addToFavourites(postId, mock(User.class));
        verify(participatesService).save(postId, eq(anyInt()));
        assertThat(view).isEqualTo("redirect:/index");
    }

    @Test
    void wenPostIsSold() {
        var postId = 1;
        var view = postController.soldPost(postId);
        verify(postService).sold(postId);
        assertThat(view).isEqualTo("redirect:/index");
    }
}