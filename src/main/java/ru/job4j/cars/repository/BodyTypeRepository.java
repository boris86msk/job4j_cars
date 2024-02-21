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

    public Optional<BodyType> save(BodyType bodyType) {
        try {
            crudRepository.run(session -> session.persist(bodyType));
            return Optional.of(bodyType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
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

    public void delete(int engineId) {
        crudRepository.run(
                "delete from BodyType where id = :fId",
                Map.of("fId", engineId)
        );
    }
}
