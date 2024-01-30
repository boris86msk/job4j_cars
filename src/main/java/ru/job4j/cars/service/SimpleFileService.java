package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.CrudRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimpleFileService implements FileService {
    private final CrudRepository crudRepository;

    private final String storageDirectory;

    public SimpleFileService(CrudRepository crudRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.crudRepository = crudRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    @Override
    public File savePhoto(FileDto fileDto) {
            String path = getNewFilePath(fileDto.getName());
            writeFileBytes(path, fileDto.getContent());
            File newFile = new File();
            newFile.setPath(path);
            return newFile;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        Optional<File> fileOptional = crudRepository.optional("from File where id = :fid",
                File.class, Map.of("fid", id));
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }

        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getPath(), content));
    }


    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
