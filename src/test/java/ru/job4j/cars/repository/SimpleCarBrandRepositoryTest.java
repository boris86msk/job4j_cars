package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarBrand;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleCarBrandRepositoryTest {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static SimpleCarBrandRepository simpleCarBrandRepository = new SimpleCarBrandRepository(crudRepository);

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void wenNeedFindAllCarBrand() {
        List<String> carBrandList = List.of("AlfaRomeo", "Audi", "BMW", "Chery", "Chevrolet", "Citroen",
                "DAF", "Daewoo", "Fiat", "Ford", "GAZ", "Geely", "Honda", "Hyundai", "Isuzu", "Jaguar", "Jeep", "Kia",
                "Lada", "LandRover", "Lexus", "Man", "Mazda", "Mercedes Benz", "Mitsubishi", "Nissan", "Opel",
                "Peugeot", "Porsche", "Renault", "Rover", "Saab", "Skoda", "SsangYong", "Subaru", "Suzuki",
                "Tesla", "Toyota", "Volkswagen", "Volvo", "УАЗ");
        List<String> nameList = simpleCarBrandRepository.findAllCarBrand().stream()
                .map(CarBrand::getName)
                .toList();
        assertThat(nameList).isEqualTo(carBrandList);
    }
}