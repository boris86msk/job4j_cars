package ru.job4j.cars.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepositoryTest {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static CarRepository carRepository = new CarRepository(crudRepository);
    private static EngineRepository engineRepository = new EngineRepository(crudRepository);
    private static Car car;
    private static Car car2;

    @BeforeEach
    public void addDataToDataBase() {
        Engine engine = new Engine();
        engine.setName("ВАЗ-21129");
        engineRepository.save(engine);

        car = new Car();
        car.setBrand("Lada");
        car.setModel("Vesta");
        car.setEngine(engine);
        carRepository.save(car);

        car2 = new Car();
        car2.setBrand("Lada");
        car2.setModel("Granta");
        car2.setEngine(engine);
        carRepository.save(car2);
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    public void wipeTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE Car")
                    .executeUpdate();
            session.createQuery("DELETE Engine")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void wenSaveCarAndFindCarById() {
        int id = car.getId();
        assertThat(carRepository.findById(id)).isNotEmpty();
        assertThat(carRepository.findById(id).get()).isEqualTo(car);

    }

    @Test
    public void wenNeedFindAllCars() {
        var resultList = List.of(car, car2);
        assertThat(carRepository.findAllCar()).isEqualTo(resultList);
    }

    @Test
    public void wenHaveCarAndUpdateIt() {
        String carModel = "Vesta_II";
        car.setModel(carModel);
        carRepository.update(car);
        int id = car.getId();
        assertThat(carRepository.findById(id)).isNotEmpty();
        assertThat(carRepository.findById(id).get().getModel()).isEqualTo(carModel);
    }

    @Test
    public void wenWeDeleteCarFromDbById() {
        int id = car.getId();
        carRepository.delete(id);
        assertThat(carRepository.findById(id)).isEmpty();
    }
}