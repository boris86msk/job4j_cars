package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.SimplePostRepository;
import ru.job4j.cars.repository.ParticipatesRepository;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimplePostServiceTest {
    private static PriceHistoryRepository priceHistoryRepository = mock(PriceHistoryRepository.class);
    private static ParticipatesRepository participatesRepository = mock(ParticipatesRepository.class);
    private static BodyTypeService bodyTypeService = mock(BodyTypeService.class);
    private static SimplePostRepository simplePostRepository = mock(SimplePostRepository.class);
    private static FileService fileService = mock(FileService.class);
    private static PostService postService = new SimplePostService(priceHistoryRepository, participatesRepository,
            bodyTypeService, simplePostRepository, fileService);

    @Test
    void wenFindAllPost() {
        var expectedList = List.of(new Post(), new Post());
        when(simplePostRepository.findAll()).thenReturn(expectedList);
        var returnValue = postService.findAll();
        assertThat(returnValue).isEqualTo(expectedList);
    }

    @Test
    void wenFindPostListByMaxPrice() {
        int maxPrice = 100;
        var expectedList = List.of(new Post(), new Post());
        var priceCaptor = ArgumentCaptor.forClass(Integer.class);
        when(simplePostRepository.findByMaxPrice(priceCaptor.capture())).thenReturn(expectedList);
        var returnValue = postService.findByMaxPrice(maxPrice);
        assertThat(priceCaptor.getValue()).isEqualTo(maxPrice);
        assertThat(returnValue).isEqualTo(expectedList);
    }

    @Test
    void wenFindListPostByToday() {
        var expectedList = List.of(new Post(), new Post());
        when(simplePostRepository.findByToday()).thenReturn(expectedList);
        var returnValue = postService.findByToday();
        assertThat(returnValue).isEqualTo(expectedList);
    }

    @Test
    void wenFindPostListByBrand() {
        var brand = "Lada";
        var expectedList = List.of(new Post(), new Post());
        var brandCaptor = ArgumentCaptor.forClass(String.class);
        when(simplePostRepository.findByBrand(brandCaptor.capture())).thenReturn(expectedList);
        var returnValue = postService.findByBrand(brand);
        assertThat(brandCaptor.getValue()).isEqualTo(brand);
        assertThat(returnValue).isEqualTo(expectedList);
    }

    @Test
    void wenFindPostListByUserId() {
        var userId = 10;
        var expectedList = List.of(new Post(), new Post());
        var userIdCaptor = ArgumentCaptor.forClass(Integer.class);
        when(simplePostRepository.findByUser(userIdCaptor.capture())).thenReturn(expectedList);
        var returnValue = postService.findByUser(userId);
        assertThat(userIdCaptor.getValue()).isEqualTo(userId);
        assertThat(returnValue).isEqualTo(expectedList);
    }

    @Test
    void wenSavePriceHistory() {
        Post post = new Post();
        var priceHistory = new PriceHistory();
        when(priceHistoryRepository.save(any(PriceHistory.class))).thenReturn(Optional.of(priceHistory));
        var returnValue = postService.savePriceHistory(post);
        assertThat(returnValue.get()).isEqualTo(priceHistory);
    }

    @Test
    void wenFindPostById() {
        int postId = 5;
        Post post = new Post();
        post.setId(postId);
        var postIdCaptor = ArgumentCaptor.forClass(Integer.class);
        when(simplePostRepository.findById(postIdCaptor.capture())).thenReturn(Optional.of(post));
        var returnValue = postService.findById(postId);
        assertThat(returnValue.get()).isEqualTo(post);
        assertThat(postIdCaptor.getValue()).isEqualTo(postId);
    }

}