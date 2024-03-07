package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarBrand;

import java.util.List;

public interface CarBrandRepository {
    List<CarBrand> findAllCarBrand();
}
