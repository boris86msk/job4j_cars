package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OwnerRepository {
    private final CrudRepository crudRepository;

    public OwnerRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    /**
     * Сохранить в базе.
     * @param owner владелец.
     * @return владелец с id.
     */
    public Optional<Owner> save(Owner owner) {
        try {
            crudRepository.run(session -> session.persist(owner));
            return Optional.of(owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Найти всех владельцев.
     * @return Список Owner, сортеровка по id.
     */
    public List<Owner> findAllOwner() {
        return crudRepository.query(
                "from Owner o"
                        + " left join fetch o.history"
                        + " order by o.id", Owner.class
        );
    }

    /**
     * Найти владельца по id
     * @param id
     * @return Owner по id
     */
    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "from Owner o"
                        + " left join fetch o.history"
                        + " where o.id = :fownerId", Owner.class,
                Map.of("fownerId", id)
        );
    }

    /**
     * Обновить владельца в базе.
     * @param owner
     */
    public void update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
    }

    /**
     * Удалить владельца по id.
     * @param ownerId
     */
    public void delete(int ownerId) {
        crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", ownerId)
        );
    }
}
