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

    /**
     * Сохранить в базе.
     * @param bodyType тип кузова.
     * @return тип кузова с id.
     */
    public Optional<BodyType> save(BodyType bodyType) {
        try {
            crudRepository.run(session -> session.persist(bodyType));
            return Optional.of(bodyType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Получить список типов кузова.
     * @return Список BodyType, сортеровка по id.
     */
    public List<BodyType> findAllEngine() {
        return crudRepository.query(
                "from BodyType order by id", BodyType.class
        );
    }

    /**
     * Найти тип кузова по id
     * @param id
     * @return BodyType по id
     */
    public Optional<BodyType> findById(int id) {
        return crudRepository.optional(
                "from BodyType where id = :fbodyId", BodyType.class,
                Map.of("fbodyId", id)
        );
    }

    /**
     * Удалить тип кузова по id.
     * @param engineId
     */
    public void delete(int engineId) {
        crudRepository.run(
                "delete from BodyType where id = :fId",
                Map.of("fId", engineId)
        );
    }
}
