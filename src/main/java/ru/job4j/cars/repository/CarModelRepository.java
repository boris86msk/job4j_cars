package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBrand;

import java.util.List;

@Repository
public class CarModelRepository {
    private final CrudRepository crudRepository;

    public CarModelRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<CarBrand> findAllCarBrand() {
        return crudRepository.query(
                "from CarBrand", CarBrand.class
        );
    }
}
