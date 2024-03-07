package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.FirstCarBrandRepository;

import java.util.List;

@Service
public class SimpleCarBrandService implements CarBrandService {
    private final FirstCarBrandRepository firstCarBrandRepository;

    public SimpleCarBrandService(FirstCarBrandRepository firstCarBrandRepository) {
        this.firstCarBrandRepository = firstCarBrandRepository;
    }

    @Override
    public List<CarBrand> findAllBrand() {
        return firstCarBrandRepository.findAllCarBrand();
    }
}
