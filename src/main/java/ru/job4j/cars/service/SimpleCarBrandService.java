package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.SimpleCarBrandRepository;

import java.util.List;

@Service
public class SimpleCarBrandService implements CarBrandService {
    private final SimpleCarBrandRepository simpleCarBrandRepository;

    public SimpleCarBrandService(SimpleCarBrandRepository simpleCarBrandRepository) {
        this.simpleCarBrandRepository = simpleCarBrandRepository;
    }

    @Override
    public List<CarBrand> findAllBrand() {
        return simpleCarBrandRepository.findAllCarBrand();
    }
}
