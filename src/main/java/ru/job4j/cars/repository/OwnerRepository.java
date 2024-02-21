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

    public Optional<Owner> save(Owner owner) {
        try {
            crudRepository.run(session -> session.persist(owner));
            return Optional.of(owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Owner> findAllOwner() {
        return crudRepository.query(
                "from Owner o"
                        + " left join fetch o.history"
                        + " order by o.id", Owner.class
        );
    }

    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "from Owner o"
                        + " left join fetch o.history"
                        + " where o.id = :fownerId", Owner.class,
                Map.of("fownerId", id)
        );
    }

    public void update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
    }

    public void delete(int ownerId) {
        crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", ownerId)
        );
    }
}
