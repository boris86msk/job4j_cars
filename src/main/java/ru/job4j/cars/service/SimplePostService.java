package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.ParticipatesRepository;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimplePostService implements PostService {
    private final PriceHistoryRepository priceHistoryRepository;
    private final ParticipatesRepository participatesRepository;
    private final PostRepository postRepository;
    private final FileService fileService;

    public SimplePostService(PriceHistoryRepository priceHistoryRepository,
                             ParticipatesRepository participatesRepository,
                             PostRepository postRepository,
                             FileService fileService) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.participatesRepository = participatesRepository;
        this.postRepository = postRepository;
        this.fileService = fileService;
    }

    /**
     * Метод преобразует лист истории цены для представления index.html,
     * оставляя из принемаемого листа только первое и последнее знаение
     */
    public List<Post> priceListSort(List<Post> postList) {
        for (Post post : postList) {
            List<PriceHistory> historyList = post.getHistoryList();
            if (historyList.size() > 2) {
                List<PriceHistory> newList = new ArrayList<>();
                newList.add(historyList.get(0));
                newList.add(historyList.get(historyList.size() - 1));
                post.setHistoryList(newList);
            }
        }
        return postList;
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
    public Optional<Post> save(Post post) {
        return postRepository.save(post);
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
    public void deletePost(int postId) {
        participatesRepository.delete(postId);
        priceHistoryRepository.delete(postId);
        postRepository.delete(postId);
    }

    @Override
    public void sold(int postId) {
        postRepository.sold(postId);
    }
}
