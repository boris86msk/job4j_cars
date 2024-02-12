package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> saveUser(User user);
    Optional<User> findByEmailAndPassword(String login, String pass);
    Optional<User> findById(int userId);
}
