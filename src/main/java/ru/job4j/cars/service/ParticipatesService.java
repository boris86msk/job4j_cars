package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

public interface ParticipatesService {
    void save(int postId, int userId);

    void delete(int postId);
}
