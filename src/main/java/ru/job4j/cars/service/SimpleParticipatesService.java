package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.ParticipatesRepository;

@Service
public class SimpleParticipatesService implements ParticipatesService {
    private final ParticipatesRepository participatesRepository;

    public SimpleParticipatesService(ParticipatesRepository participatesRepository) {
        this.participatesRepository = participatesRepository;
    }

    @Override
    public void save(int postId, int userId) {
        participatesRepository.save(postId, userId);
    }

    @Override
    public void deleteByPostId(int postId) {
        participatesRepository.delete(postId);
    }
}
