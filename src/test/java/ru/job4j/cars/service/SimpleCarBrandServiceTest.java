package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.FirstCarBrandRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleCarBrandServiceTest {
    private static FirstCarBrandRepository firstCarBrandRepository = mock(FirstCarBrandRepository.class);
    private static CarBrandService carBrandService = new SimpleCarBrandService(firstCarBrandRepository);
    @Test
    void wenFindAllBrand() {
        var expectedList = List.of(new CarBrand(), new CarBrand());
        when(firstCarBrandRepository.findAllCarBrand()).thenReturn(expectedList);
        var returnList = carBrandService.findAllBrand();
        assertThat(returnList).isEqualTo(expectedList);
    }
}