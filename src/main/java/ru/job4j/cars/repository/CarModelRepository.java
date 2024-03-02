package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;

import java.util.List;

@Repository
public class CarModelRepository {
    private final CrudRepository crudRepository;

    public CarModelRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<CarModel> findAllCarModel() {
        return crudRepository.query(
                "from CarModel", CarModel.class
        );
    }
}
