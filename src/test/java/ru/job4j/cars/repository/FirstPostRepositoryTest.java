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
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FirstPostRepositoryTest {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private static CrudRepository crudRepository = new CrudRepository(sf);
    private static UserRepository userRepository = new UserRepository(crudRepository);
    private static FirstPostRepository firstPostRepository = new FirstPostRepository(crudRepository);
    private static Post post;
    private static Post post2;
    private static Post post3;

    @BeforeEach
    public void initDataForDb() {
        BodyType bodyType = new BodyType();
        bodyType.setId(1);
        bodyType.setName("Седан");

        CarBrand carBrand = new CarBrand();
        carBrand.setId(2);
        carBrand.setName("Audi");

        CarBrand carBrand2 = new CarBrand();
        carBrand2.setId(18);
        carBrand2.setName("Lada");

        Car car = new Car();
        car.setBrand(carBrand);
        car.setModel("A4");
        car.setBodyType(bodyType);
        car.setFuel("Бензин");
        car.setAge(2018);
        car.setMileage(100000);

        Car car2 = new Car();
        car2.setBrand(carBrand2);
        car2.setModel("Vesta2");
        car2.setBodyType(bodyType);
        car2.setFuel("Бензин");
        car2.setAge(2020);
        car2.setMileage(90000);

        Car car3 = new Car();
        car3.setBrand(carBrand2);
        car3.setModel("Kalina");
        car3.setBodyType(bodyType);
        car3.setFuel("Дизель");
        car3.setAge(2015);
        car3.setMileage(25000);

        User user = new User();
        user.setLogin("myLogin");
        user.setPassword("1111");
        user.setName("Иванов Иван Ивыныч");

        User user2 = new User();
        user2.setLogin("myLogin2");
        user2.setPassword("2222");
        user2.setName("Петров Петр Петрович");

        userRepository.create(user);
        userRepository.create(user2);

        post = new Post();
        post.setDescription("test_description");
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        post.setUser(user);
        post.setCar(car);
        post.setFile(new File());

        post2 = new Post();
        post2.setDescription("test_description_2");
        post2.setCreated(LocalDateTime.of(2024, Month.JANUARY, 5, 12, 0, 0));
        post2.setUser(user);
        post2.setCar(car2);

        post3 = new Post();
        post3.setDescription("test_description_3");
        post3.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        post3.setUser(user2);
        post3.setCar(car3);

        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.persist(post);
            session.persist(post2);
            session.persist(post3);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
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
            session.createQuery("DELETE Post")
                    .executeUpdate();
            session.createQuery("DELETE File")
                    .executeUpdate();
            session.createQuery("DELETE Car")
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
    public void wenSaveNewPost() {
        Post testPost = new Post();
        testPost.setDescription("test_description");
        testPost.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        User user = new User();
        user.setLogin("login");
        user.setPassword("pass");
        user.setName("Some name");
        userRepository.create(user);
        BodyType bodyType = new BodyType();
        bodyType.setId(4);
        bodyType.setName("Кроссовер");
        CarBrand carBrand = new CarBrand();
        carBrand.setId(10);
        carBrand.setName("Ford");
        Car car = new Car();
        car.setBrand(carBrand);
        car.setModel("Model");
        car.setBodyType(bodyType);
        car.setFuel("Бензин");
        car.setAge(2018);
        car.setMileage(100000);
        File file = new File();
        file.setPath("some_path");
        testPost.setFile(file);
        testPost.setUser(user);
        testPost.setCar(car);
        firstPostRepository.save(testPost);
        int id = testPost.getId();
        assertThat(firstPostRepository.findById(id).get()).isEqualTo(testPost);
    }

    @Test
    public void wenFindAllPosts() {
        var expectedList = List.of(post, post2, post3);
        assertThat(firstPostRepository.findAll()).containsAll(expectedList);
    }

    @Test
    public void wenFindPostsByToDay() {
        var expectedList = List.of(post, post3);
        assertThat(firstPostRepository.findByToday()).containsAll(expectedList);
    }

    @Test
    public void wenFindAllPostsWhereFilesNotNull() {
        Post ps1 = firstPostRepository.findById(post.getId()).get();
        Post ps2 = firstPostRepository.findById(post2.getId()).get();
        var expectedList = List.of(ps1);
        var notExpected = List.of(ps2);
        assertThat(firstPostRepository.findAllWhereFilesNotNull()).isEqualTo(expectedList);
        assertThat(firstPostRepository.findAllWhereFilesNotNull()).isNotEqualTo(notExpected);
    }

    @Test
    public void wenFindAllPostByCarModel() {
        var expectedList = List.of(post);
        assertThat(firstPostRepository.findByBrand("Audi")).isEqualTo(expectedList);

    }
}