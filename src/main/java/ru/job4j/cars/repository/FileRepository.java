package ru.job4j.cars.repository;

import ru.job4j.cars.model.Files;

import java.util.Optional;

public class FileRepository {
    private final CrudRepository crudRepository;

    public FileRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    /**
     * Сохранить в базе.
     * @param file двигатель.
     * @return файл с id.
     */
    public Optional<Files> save(Files file) {
        try {
            crudRepository.run(session -> session.persist(file));
            return Optional.of(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
