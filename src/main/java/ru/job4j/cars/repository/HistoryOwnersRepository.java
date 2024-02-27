package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class HistoryOwnersRepository {
    private final CrudRepository crudRepository;

    public HistoryOwnersRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public boolean save(int carId, int ownerId) {
        try {
        crudRepository.run(
                "insert history_owners(car_id, owner_id) values(:carId, :ownerId)",
                Map.of("carId", carId, "ownerId", ownerId)
        );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
