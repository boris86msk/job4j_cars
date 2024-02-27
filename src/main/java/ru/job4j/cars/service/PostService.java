package ru.job4j.cars.service;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<PriceHistory> savePriceHistory(Post post);
    Optional<Post> savePost(Post post, MultipartFile myFile, User user) throws IOException;
    Optional<Post> findById(int id);
    void updateById(int postId, int price);
    boolean deletePost(int postId, int fileId) throws IOException;
    void sold(int postId);
}
