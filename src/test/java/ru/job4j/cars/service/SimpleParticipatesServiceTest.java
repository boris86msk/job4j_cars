package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.job4j.cars.repository.ParticipatesRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SimpleParticipatesServiceTest {
    private static ParticipatesRepository participatesRepository = mock(ParticipatesRepository.class);
    private static SimpleParticipatesService participatesService = new SimpleParticipatesService(participatesRepository);

    @Test
    void wenSaveParticipates() {
        int postId = 1;
        int userId = 3;
        var postCaptor = ArgumentCaptor.forClass(Integer.class);
        var userCaptor = ArgumentCaptor.forClass(Integer.class);
        participatesService.save(postId, userId);
        verify(participatesRepository).save(postCaptor.capture(), userCaptor.capture());
        assertThat(postCaptor.getValue()).isEqualTo(postId);
        assertThat(userCaptor.getValue()).isEqualTo(userId);
    }

    @Test
    void wenDeletePostById() {
        int postId = 1;
        var postCaptor = ArgumentCaptor.forClass(Integer.class);
        participatesService.deleteByPostId(postId);
        verify(participatesRepository).delete(postCaptor.capture());
        assertThat(postCaptor.getValue()).isEqualTo(postId);
    }
}