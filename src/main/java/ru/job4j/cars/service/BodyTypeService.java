package ru.job4j.cars.service;

import ru.job4j.cars.model.BodyType;

import java.util.List;
import java.util.Optional;

public interface BodyTypeService {
    List<BodyType> findAllType();
    Optional<BodyType> findById(int id);
}
