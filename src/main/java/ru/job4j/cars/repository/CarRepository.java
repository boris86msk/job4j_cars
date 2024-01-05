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

    /**
     * Сохранить в базе.
     * @param car автомобиль.
     * @return автомобиль с id.
     */
    public Optional<Car> save(Car car) {
        try {
            crudRepository.run(session -> session.persist(car));
            return Optional.of(car);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Найти все автомобили.
     * @return Список Car, сортеровка по id.
     */
    public List<Car> findAllCar() {
        return crudRepository.query(
                "from Car c left join fetch c.owners order by c.id", Car.class
        );
    }

    /**
     * Найти автомобиль по id
     * @param id
     * @return Car по id
     */
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car c left join fetch c.owners where c.id = :fcarId", Car.class,
                Map.of("fcarId", id)
        );
    }

    /**
     * Обновить автомобиль в базе.
     * @param car
     */
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить автомобиль по id.
     * @param carId
     */
    public void delete(int carId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", carId)
        );
    }
}
