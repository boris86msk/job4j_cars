package ru.job4j.cars.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.BodyType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class BodyTypeRepositoryTest {

    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static BodyTypeRepository bodyTypeRepository = new BodyTypeRepository(crudRepository);

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void wenNeedFindAllBodyType() {
        List<String> bodyTypeList = List.of("Сидан", "Хечбек", "Универсал", "Кроссовер",
                "Внедорожник", "Минивэн", "Пикап", "Купе", "Кабриолет", "Лимузин");

        List<String> nameList = bodyTypeRepository.findAllType().stream()
                .map(BodyType::getName)
                .toList();
        assertThat(nameList).isEqualTo(bodyTypeList);
    }

    @Test
    public void wenFindBodyTypeById() {
        BodyType bodyType = new BodyType();
        bodyType.setId(7);
        bodyType.setName("Пикап");
        int findId = 7;

        assertThat(bodyTypeRepository.findById(findId).get()).isEqualTo(bodyType);
        assertThat(bodyTypeRepository.findById(findId + 1).get()).isNotEqualTo(bodyType);
    }
}