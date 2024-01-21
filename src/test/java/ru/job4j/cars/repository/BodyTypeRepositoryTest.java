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

    @AfterEach
    public void wipeTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE BodyType")
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
    public void wenSaveEngine() {
        BodyType bodyType = new BodyType();
        bodyType.setName("Родстер");
        bodyTypeRepository.save(bodyType);
        int id = bodyType.getId();
        assertThat(bodyTypeRepository.findById(id)).isNotEmpty();
        assertThat(bodyTypeRepository.findById(id).get()).isEqualTo(bodyType);
    }

    @Test
    public void wenNeedFindAllEngine() {
        BodyType bodyType1 = new BodyType();
        BodyType bodyType2 = new BodyType();
        bodyType1.setName("Сидан");
        bodyType2.setName("Купе");
        bodyTypeRepository.save(bodyType1);
        bodyTypeRepository.save(bodyType2);
        List<BodyType> bodyTypeList = List.of(bodyType1, bodyType2);
        assertThat(bodyTypeRepository.findAllType()).isEqualTo(bodyTypeList);
    }

    @Test
    public void wenFindEngineById() {
        BodyType bodyType = new BodyType();
        bodyType.setName("Пикап");
        bodyTypeRepository.save(bodyType);
        int id = bodyType.getId();
        assertThat(bodyTypeRepository.findById(id).get()).isEqualTo(bodyType);
        assertThat(bodyTypeRepository.findById(id + 1)).isEmpty();
    }

    @Test
    public void wenNeedDeleteEngine() {
        BodyType bodyType = new BodyType();
        bodyType.setName("Хечбэк");
        bodyTypeRepository.save(bodyType);
        int id = bodyType.getId();
        bodyTypeRepository.delete(id);
        assertThat(bodyTypeRepository.findById(id)).isEmpty();
    }
  
}