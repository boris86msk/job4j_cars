package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;

import java.io.IOException;
import java.util.Optional;

public interface FileService {
    File savePhoto(FileDto fileDto) throws IOException;

    Optional<FileDto> getFileById(int id);

    //void deleteById(int id);
}
