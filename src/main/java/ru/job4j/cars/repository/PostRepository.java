package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> save(Post post);
    List<Post> findAll();
    List<Post> findByToday();
    List<Post> findAllWhereFilesNotNull();
    List<Post> findByBrand(String brand);
    Optional<Post> findById(int id);
    List<Post> findByUser(int userId);
    List<Post> findByMaxPrice(int price);
    void updateById(int id, int price);
    void sold(int postId);
    String delete(int postId, int fileId);
}
