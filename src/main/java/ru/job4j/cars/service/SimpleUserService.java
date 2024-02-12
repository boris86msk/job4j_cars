package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> saveUser(User user) {
        return userRepository.create(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String login, String pass) {
        return userRepository.findByLoginAndPassword(login, pass);
    }

    @Override
    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);
    }
}
