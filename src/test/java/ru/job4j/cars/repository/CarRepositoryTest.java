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
import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.CarBrand;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepositoryTest {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static CarRepository carRepository = new CarRepository(crudRepository);
    private static BodyTypeRepository bodyTypeRepository = new BodyTypeRepository(crudRepository);
    private static Car car;
    private static Car car2;

    @BeforeEach
    public void addDataToDataBase() {
        BodyType bodyType = new BodyType();
        bodyType.setId(1);
        bodyType.setName("Сидан");

        BodyType bodyType2 = new BodyType();
        bodyType2.setId(2);
        bodyType2.setName("Хечбек");

        CarBrand carBrand = new CarBrand();
        carBrand.setId(1);
        carBrand.setName("AlfaRomeo");

        car = new Car();
        car.setBrand(carBrand);
        car.setModel("Vesta");
        car.setBodyType(bodyType);
        car.setFuel("Бензин");
        car.setAge(2018);
        car.setMileage(100000);
        carRepository.save(car);

        car2 = new Car();
        car2.setBrand(carBrand);
        car2.setModel("Granta");
        car2.setBodyType(bodyType2);
        car2.setFuel("Бензин");
        car2.setAge(2016);
        car2.setMileage(150000);
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