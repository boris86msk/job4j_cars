package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CarRepository {
    private final CrudRepository crudRepository;

    public CarRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Optional<Car> save(Car car) {
        try {
            crudRepository.run(session -> session.persist(car));
            return Optional.of(car);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Car> findAllCar() {
        return crudRepository.query(
                "from Car c left join fetch c.owners order by c.id", Car.class
        );
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car c left join fetch c.owners where c.id = :fcarId", Car.class,
                Map.of("fcarId", id)
        );
    }

    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    public void delete(int carId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", carId)
        );
    }
}
