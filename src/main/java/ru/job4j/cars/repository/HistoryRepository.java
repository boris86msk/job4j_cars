package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class HistoryRepository {
    private final CrudRepository crudRepository;

    public HistoryRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public boolean saveStartAt(LocalDateTime startAt, int ownerId) {
        try {
            crudRepository.run(
                    "insert history(startat, owner_id) values(:start, :ownerId)",
                    Map.of("start", startAt,  "owner_id", ownerId)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveEndAt(LocalDateTime endAt, int ownerId) {
        try {
            crudRepository.run(
                    "update history set endat = :end where owner_id = :ownerId",
                    Map.of("end", endAt,  "ownerId", ownerId)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
