package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Optional<Car> save(Car car);

    List<Car> findAllCar();

    Optional<Car> findById(int id);

    void update(Car car);

    void delete(int carId);
}
