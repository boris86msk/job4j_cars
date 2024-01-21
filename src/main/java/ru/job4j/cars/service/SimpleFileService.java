package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleFileService implements FileService {
    @Override
    public List<Files> savePhoto(MultipartFile[] files) {
        List<Files> filesList = new ArrayList<>();
        for (MultipartFile file : files) {
            if(!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    FileCopyUtils.copy(bytes, new File("src/main/resources/static/img/" + file.getOriginalFilename()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Files newFile = new Files();
            newFile.setPath("img/" + file.getOriginalFilename());
            filesList.add(newFile);
        }
        return filesList;
    }
}
