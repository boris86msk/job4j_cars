package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> priceListSort(List<Post> list);
    Optional<PriceHistory> savePriceHistory(Post post);
    void delete(int postId);
    void sold(int postId);
}
