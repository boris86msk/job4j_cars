package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> priceListSort(List<Post> list);
    Optional<PriceHistory> savePriceHistory(Post post);
    Optional<Post> save(Post post);
    Optional<Post> findById(int id);
    void updateById(int postId, int price);
    void deletePost(int postId, int fileId) throws IOException;
    void sold(int postId);
}
