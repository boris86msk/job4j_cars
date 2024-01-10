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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static PostRepository postRepository = new PostRepository(crudRepository);
    private static Post post;
    private static Post post2;
    private static Post post3;

    @BeforeEach
    public void initDataForDb() {
        Engine engine = new Engine();
        engine.setName("ВАЗ-21129");

        Car car = new Car();
        car.setBrand("Lada");
        car.setModel("Vesta");
        car.setEngine(engine);

        Car car2 = new Car();
        car2.setBrand("Lada");
        car2.setModel("Vesta2");
        car2.setEngine(engine);

        User user = new User();
        user.setLogin("myLogin");
        user.setPassword("1111");
        user.setCars(Set.of(car));

        User user2 = new User();
        user2.setLogin("myLogin2");
        user2.setPassword("2222");

        Files file = new Files();
        file.setPath("somePath");

        post = new Post();
        post.setDescription("test_description");
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        post.setUser(user);
        post.setFiles(Set.of(file));

        post2 = new Post();
        post2.setDescription("test_description_2");
        post2.setCreated(LocalDateTime.of(2024, Month.JANUARY, 5, 12, 0, 0));
        post2.setUser(user);

        post3 = new Post();
        post3.setDescription("test_description_3");
        post3.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        post3.setUser(user2);

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
            session.createQuery("DELETE Files")
                    .executeUpdate();
            session.createQuery("DELETE Post")
                    .executeUpdate();
            session.createQuery("DELETE Car")
                    .executeUpdate();
            session.createQuery("DELETE Engine")
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
        testPost.setDescription("in_test_description");
        testPost.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        User user = new User();
        user.setLogin("login");
        user.setPassword("pass");
        testPost.setUser(user);
        postRepository.save(testPost);
        int id = testPost.getId();
        assertThat(postRepository.findById(id).get()).isEqualTo(testPost);
    }

    @Test
    public void wenFindAllPosts() {
        var expectedList = List.of(post, post2, post3);
        assertThat(postRepository.findAll()).isEqualTo(expectedList);
    }

    @Test
    public void wenFindPostsByToDay() {
        var expectedList = List.of(post, post3);
        assertThat(postRepository.findByToday()).isEqualTo(expectedList);
    }

    @Test
    public void wenFindAllPostsWhereFilesNotNull() {
        Post ps1 = postRepository.findById(post.getId()).get();
        Post ps2 = postRepository.findById(post2.getId()).get();
        var expectedList = List.of(ps1);
        var notExpected = List.of(ps2);
        assertThat(postRepository.findAllWhereFilesNotNull()).isEqualTo(expectedList);
        assertThat(postRepository.findAllWhereFilesNotNull()).isNotEqualTo(notExpected);
    }

    @Test
    public void wenFindAllPostByCarModel() {
        var expectedList = List.of(post, post2);
        List<Post> vesta = postRepository.findByModel("Vesta");
        assertThat(postRepository.findByModel("Vesta")).isEqualTo(expectedList);

    }
}