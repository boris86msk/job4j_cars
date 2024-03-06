package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.CarModelRepository;

import java.util.List;

@Service
public class SimpleCarBrandService implements CarBrandService {
    private final CarModelRepository carModelRepository;

    public SimpleCarBrandService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public List<CarBrand> findAllBrand() {
        return carModelRepository.findAllCarBrand();
    }
}
