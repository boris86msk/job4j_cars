package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    public UserService userService;
    public PostService postService;
    public UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        postService = mock(PostService.class);
        userController = new UserController(userService, postService);
    }

    @Test
    void whenSuccessfullyUserLogin() {
        var user = new User(1, "moc@mail.ru", "1111", "Boris", List.of());
        var loginCaptor = ArgumentCaptor.forClass(String.class);
        var passCaptor = ArgumentCaptor.forClass(String.class);
        when(userService.findByEmailAndPassword(loginCaptor.capture(), passCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        var view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(loginCaptor.getValue()).isEqualTo(user.getLogin());
        assertThat(passCaptor.getValue()).isEqualTo(user.getPassword());
    }

    @Test
    void whenUnsuccessfullyUserLogin() {
        var user = new User();
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        var view = userController.loginUser(user, model, request);
        var actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualMessage).isEqualTo("Почта или пароль введены неверно");
    }

    @Test
    void wenGetRegistrationPage() {
        String view = userController.getRegistrationPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    public void whenSuccessfullyNewUserRegistered() {
        var user = new User();
        var captor = ArgumentCaptor.forClass(User.class);
        when(userService.saveUser(captor.capture())).thenReturn(Optional.of(user));
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        var view = userController.registration(model, user, request);
        var actualUser = captor.getValue();

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenUserHasNotRegistered() {
        var user = new User();
        when(userService.saveUser(any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        var view = userController.registration(model, user, request);
        var errorMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/error");
        assertThat(errorMessage).isEqualTo("Пользователь с таким логином уже существует");
    }

    @Test
    void wenLoginUser() {
        String view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

    @Test
    void wenGetPersonalAccountPage() {
        var user = new User(1, "moc@mail.ru", "1111", "Boris", List.of());
        var captor = ArgumentCaptor.forClass(Integer.class);
        when(userService.findById(captor.capture())).thenReturn(Optional.of(user));
        when(postService.findByUser(captor.capture())).thenReturn(List.of());

        var model = new ConcurrentModel();
        var view = userController.getPersonalAccountPage(model, user);
        assertThat(view).isEqualTo("users/accountpage");
    }

    @Test
    void wenLogout() {
        var session = new MockHttpSession();
        String view = userController.logout(session);
        assertThat(view).isEqualTo("redirect:/login");
    }
}