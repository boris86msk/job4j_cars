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
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerRepositoryTest {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

    private  static List<Owner> ownerList;
    @BeforeEach
    public void addDataToDataBase() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("1111");
        user.setName("Иванов Иван Ивыныч");
        crudRepository.run(session -> session.persist(user));

        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("2222");
        user2.setName("Петров Петр Петрович");
        crudRepository.run(session -> session.persist(user2));

        Owner owner = new Owner();
        owner.setName("Owner1");
        owner.setUser(user);
        crudRepository.run(session -> session.persist(owner));

        Owner owner2 = new Owner();
        owner2.setName("Owner2");
        owner2.setUser(user2);
        crudRepository.run(session -> session.persist(owner2));

        ownerList = List.of(owner, owner2);
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
            session.createQuery("DELETE Owner")
                    .executeUpdate();
            session.createQuery("DELETE User")
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
    public void wenSaveNewOwnerAndFindHisById() {
        User user = new User();
        user.setLogin("loginTest");
        user.setPassword("1111");
        user.setName("Павлов Анатолий");
        crudRepository.run(session -> session.persist(user));
        Owner owner = new Owner();
        owner.setName("TestOwner");
        owner.setUser(user);
        ownerRepository.save(owner);
        int ownerId = owner.getId();
        assertThat(ownerRepository.findById(ownerId).get()).isEqualTo(owner);
    }

    @Test
    public void wenFindAllOwners() {
        assertThat(ownerRepository.findAllOwner()).isEqualTo(ownerList);
    }

    @Test
    public void wenUpdateOwner() {
        Owner owner = ownerList.get(0);
        owner.setName("Owner3");
        ownerRepository.update(owner);
        int ownerId = owner.getId();
        Owner fromDbOwner = ownerRepository.findById(ownerId).get();
        assertThat(fromDbOwner.getName()).isEqualTo("Owner3");
    }

    @Test
    public void wenDeleteOwnerById() {
        Owner owner = ownerList.get(0);
        int ownerId = owner.getId();
        ownerRepository.delete(ownerId);
        assertThat(ownerRepository.findById(ownerId)).isEmpty();
    }
}