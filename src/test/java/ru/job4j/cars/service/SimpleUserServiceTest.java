package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleUserServiceTest {
    private static UserRepository userRepository = mock(UserRepository.class);
    private static SimpleUserService simpleUserService = new SimpleUserService(userRepository);

    @Test
    void wenSaveUser() {
        User user = new User();
        when(userRepository.create(user)).thenReturn(Optional.of(user));
        var returnValue = simpleUserService.saveUser(user);
        assertThat(returnValue.get()).isEqualTo(user);
    }

    @Test
    void wenFindByEmailAndPassword() {
       User user = new User(1, "login", "pass", "name", List.of());
        var loginCaptor = ArgumentCaptor.forClass(String.class);
        var passCaptor = ArgumentCaptor.forClass(String.class);
        when(userRepository.findByLoginAndPassword(loginCaptor.capture(), passCaptor.capture()))
                .thenReturn(Optional.of(user));
        var returnValue = simpleUserService.findByEmailAndPassword(user.getLogin(), user.getPassword());
        assertThat(returnValue.get()).isEqualTo(user);
        assertThat(loginCaptor.getValue()).isEqualTo(user.getLogin());
        assertThat(passCaptor.getValue()).isEqualTo(user.getPassword());
    }

    @Test
    void wenFindById() {
        User user = new User();
        var userId = 1;
        var idCaptor = ArgumentCaptor.forClass(Integer.class);
        when(userRepository.findById(idCaptor.capture())).thenReturn(Optional.of(user));
        var returnValue = simpleUserService.findById(userId);
        assertThat(returnValue.get()).isEqualTo(user);
        assertThat(idCaptor.getValue()).isEqualTo(userId);
    }

}