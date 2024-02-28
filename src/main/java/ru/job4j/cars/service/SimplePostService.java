package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimplePostService implements PostService {
    private final PriceHistoryRepository priceHistoryRepository;
    private final ParticipatesRepository participatesRepository;
    private final BodyTypeService bodyTypeService;
    private final PostRepository postRepository;
    private final FileService fileService;
    private final OwnerRepository ownerRepository;
    private final HistoryOwnersRepository historyOwnersRepository;
    private final HistoryRepository historyRepository;

    public SimplePostService(PriceHistoryRepository priceHistoryRepository,
                             ParticipatesRepository participatesRepository,
                             BodyTypeService bodyTypeService, PostRepository postRepository,
                             FileService fileService, OwnerRepository ownerRepository,
                             HistoryOwnersRepository historyOwnersRepository, HistoryRepository historyRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.participatesRepository = participatesRepository;
        this.bodyTypeService = bodyTypeService;
        this.postRepository = postRepository;
        this.fileService = fileService;
        this.ownerRepository = ownerRepository;
        this.historyOwnersRepository = historyOwnersRepository;
        this.historyRepository = historyRepository;
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
        Optional<Post> optionalPost = postRepository.save(post);
        if (optionalPost.isPresent()) {
            savePriceHistory(post);
            participatesRepository.save(post.getId(), user.getId());
        }
        return optionalPost;
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public void updateById(int postId, int price) {
        postRepository.updateById(postId, price);
    }

    @Override
    public boolean deletePost(int postId, int fileId) throws IOException {
        String path = postRepository.delete(postId, fileId);
        if (path != null) {
            fileService.deleteFile(path);
            return true;
        }
        return false;
    }

    @Override
    public void sold(int postId) {
        postRepository.sold(postId);
    }
}
