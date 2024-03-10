package ru.job4j.cars.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.repository.CrudRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFileServiceTest {
    private static CrudRepository crudRepository = mock(CrudRepository.class);
    private static SimpleFileService simpleFileService = new SimpleFileService(crudRepository, anyString());

}