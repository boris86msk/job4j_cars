package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    @Test
    void whenSuccessfullyGetFileById() {
        var fileService = mock(FileService.class);
        var fileController = new FileController(fileService);

        var fileDto = new FileDto();
        var content = "Some content";
        fileDto.setContent(content.getBytes());

        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.of(fileDto));
        var responseEntity = fileController.getById(any(Integer.class));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(content.getBytes());
    }

    @Test
    void whenUnsuccessfullyGetFileById() {
        var fileService = mock(FileService.class);
        var fileController = new FileController(fileService);

        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.empty());
        var responseEntity = fileController.getById(any(Integer.class));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}