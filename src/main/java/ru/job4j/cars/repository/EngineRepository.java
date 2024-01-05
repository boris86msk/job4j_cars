package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EngineRepository {
    private final CrudRepository crudRepository;

    public EngineRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    /**
     * Сохранить в базе.
     * @param engine двигатель.
     * @return двигатель с id.
     */
    public Optional<Engine> save(Engine engine) {
        try {
            crudRepository.run(session -> session.persist(engine));
            return Optional.of(engine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Найти все двигатели.
     * @return Список Engine, сортеровка по id.
     */
    public List<Engine> findAllEngine() {
        return crudRepository.query(
                "from Engine order by id", Engine.class
        );
    }

    /**
     * Найти двигатель по id
     * @param id
     * @return Engine по id
     */
    public Optional<Engine> findById(int id) {
        return crudRepository.optional(
                "from Engine where id = :fengineId", Engine.class,
                Map.of("fengineId", id)
        );
    }

    /**
     * Обновить двигатель в базе.
     * @param engine
     */
    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    /**
     * Удалить двигатель по id.
     * @param engineId
     */
    public void delete(int engineId) {
        crudRepository.run(
                "delete from Engine where id = :fId",
                Map.of("fId", engineId)
        );
    }
}
