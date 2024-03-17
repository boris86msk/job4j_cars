package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBrand;

import java.util.List;

@Repository
public class SimpleCarBrandRepository implements CarBrandRepository {
    private final CrudRepository crudRepository;

    public SimpleCarBrandRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<CarBrand> findAllCarBrand() {
        return crudRepository.query(
                "from CarBrand", CarBrand.class
        );
    }
}
