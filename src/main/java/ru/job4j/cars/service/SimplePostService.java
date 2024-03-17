package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class SimplePostService implements PostService {
    private final PriceHistoryRepository priceHistoryRepository;
    private final ParticipatesRepository participatesRepository;
    private final BodyTypeService bodyTypeService;
    private final SimplePostRepository simplePostRepository;
    private final FileService fileService;

    public SimplePostService(PriceHistoryRepository priceHistoryRepository,
                             ParticipatesRepository participatesRepository,
                             BodyTypeService bodyTypeService, SimplePostRepository simplePostRepository,
                             FileService fileService) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.participatesRepository = participatesRepository;
        this.bodyTypeService = bodyTypeService;
        this.simplePostRepository = simplePostRepository;
        this.fileService = fileService;
    }

    @Override
    public List<Post> findAll() {
        return simplePostRepository.findAll();
    }

    @Override
    public List<Post> findByMaxPrice(int price) {
        return simplePostRepository.findByMaxPrice(price);
    }

    @Override
    public List<Post> findByToday() {
        return simplePostRepository.findByToday();
    }

    @Override
    public List<Post> findByBrand(String brand) {
        return simplePostRepository.findByBrand(brand);
    }

    @Override
    public List<Post> findByUser(int userId) {
        return simplePostRepository.findByUser(userId);
    }

    @Override
    public Optional<PriceHistory> savePriceHistory(Post post) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post.getPrice());
        priceHistory.setCreated(LocalDateTime.now());
        priceHistory.setPostId(post.getId());
        return priceHistoryRepository.save(priceHistory);
    }

    @Override
    public Optional<Post> savePost(Post post, MultipartFile myFile, User user) throws IOException {
        post.setUser(user);
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        int id = post.getCar().getBodyType().getId();
        BodyType bodyType = bodyTypeService.findById(id).get();
        post.getCar().setBodyType(bodyType);
        post.setFile(fileService.savePhoto(new FileDto(myFile.getOriginalFilename(), myFile.getBytes())));
        Optional<Post> optionalPost = simplePostRepository.save(post);
        if (optionalPost.isPresent()) {
            savePriceHistory(post);
            participatesRepository.save(post.getId(), user.getId());
        }
        return optionalPost;
    }

    @Override
    public Optional<Post> findById(int id) {
        return simplePostRepository.findById(id);
    }

    @Override
    public void updateById(int postId, int price) {
        simplePostRepository.updateById(postId, price);
    }

    @Override
    public boolean deletePost(int postId, int fileId) throws IOException {
        String path = simplePostRepository.delete(postId, fileId);
        if (path != null) {
            fileService.deleteFile(path);
            return true;
        }
        return false;
    }

    @Override
    public void sold(int postId) {
        simplePostRepository.sold(postId);
    }
}
