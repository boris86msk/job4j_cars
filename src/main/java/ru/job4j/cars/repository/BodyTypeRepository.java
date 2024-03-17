package ru.job4j.cars.repository;

import ru.job4j.cars.model.BodyType;

import java.util.List;
import java.util.Optional;

public interface BodyTypeRepository {
    List<BodyType> findAllType();

    Optional<BodyType> findById(int id);
}
