package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BodyTypeRepository {
    private final CrudRepository crudRepository;

    public BodyTypeRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<BodyType> findAllType() {
        return crudRepository.query(
                "from BodyType order by id", BodyType.class
        );
    }

    public Optional<BodyType> findById(int id) {
        return crudRepository.optional(
                "from BodyType where id = :fbodyId", BodyType.class,
                Map.of("fbodyId", id)
        );
    }
}
