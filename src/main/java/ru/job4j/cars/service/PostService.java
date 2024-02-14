package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;

import java.util.List;

public interface PostService {
    List<Post> priceListSort(List<Post> list);
}
