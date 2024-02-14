package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimplePostService implements PostService {

    /**
     * Метод преобразует лист истории цены для представления index.html,
     * оставляя из принемаемого листа только первое и последнее знаение
     */
    public List<Post> priceListSort(List<Post> postList) {
        for (Post post : postList) {
            List<PriceHistory> historyList = post.getHistoryList();
            if(historyList.size() > 2) {
                List<PriceHistory> newList = new ArrayList<>();
                newList.add(historyList.get(0));
                newList.add(historyList.get(historyList.size() - 1));
                post.setHistoryList(newList);
            }
        }
        return postList;
    }
}
