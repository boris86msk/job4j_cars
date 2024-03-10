package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.repository.BodyTypeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleBodyTypeServiceTest {

    private static BodyTypeRepository bodyTypeRepository = mock(BodyTypeRepository.class);
    private static BodyTypeService bodyTypeService = new SimpleBodyTypeService(bodyTypeRepository);

    @Test
    void wenFindAllType() {
        var expectedList = List.of(new BodyType(), new BodyType());
        when(bodyTypeRepository.findAllType()).thenReturn(expectedList);
        var returnList = bodyTypeService.findAllType();
        assertThat(returnList).isEqualTo(expectedList);
    }

    @Test
    void wenFindById() {
        var expectedBodyType = Optional.of(new BodyType());
        when(bodyTypeRepository.findById(any(Integer.class))).thenReturn(expectedBodyType);
        var returnBodyType = bodyTypeRepository.findById(anyInt());
        assertThat(returnBodyType).isEqualTo(expectedBodyType);
    }

}