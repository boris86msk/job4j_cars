package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.CarBrandService;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndexControllerTest {
    public PostService postService;
    public UserService userService;
    public CarBrandService carBrandService;
    public IndexController indexController;
    public User user;
    public ConcurrentModel model;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        postService = mock(PostService.class);
        carBrandService = mock(CarBrandService.class);
        indexController = new IndexController(postService, userService, carBrandService);
        user = new User(1, "moc@mail.ru", "1111", "Boris", List.of());
        model = new ConcurrentModel();
    }

    @Test
    void wenGetIndexPage() {
        when(postService.findAll()).thenReturn(List.of());
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        var view = indexController.getIndexPage(model, user);
        assertThat(view).isEqualTo("index");
    }

    @Test
    void wenIndexPageSortByPrice() {
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        var enyPrice = "100000";
        when(postService.findAll()).thenReturn(List.of());
        var view = indexController.sortByPrice(model, user, enyPrice);
        assertThat(view).isEqualTo("index");
    }

    @Test
    void wenIndexPageSortByToday() {
        when(postService.findAll()).thenReturn(List.of());
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        var view = indexController.sortByToday(model, user);
        assertThat(view).isEqualTo("index");
    }

    @Test
    void wenIndexPageSortByBrand() {
        when(postService.findAll()).thenReturn(List.of());
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        var view = indexController.sortByBrand(model, user, any(String.class));
        assertThat(view).isEqualTo("index");
    }
}