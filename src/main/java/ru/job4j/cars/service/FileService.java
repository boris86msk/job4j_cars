package ru.job4j.cars.service;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Files;

import java.io.File;
import java.util.List;

public interface FileService {
    List<Files> savePhoto(MultipartFile[] files);
}
