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
import ru.job4j.cars.model.Files;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

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
    private static UserRepository userRepository = new UserRepository(crudRepository);
    private static FileRepository fileRepository = new FileRepository(crudRepository);
    private static Post post;
    private static Post post2;

    @BeforeEach
    public void createAndSavePostToDb() {
        User user = new User();
        user.setLogin("myLogin");
        user.setPassword("1111");
        userRepository.create(user);

        post = new Post();
        post.setDescription("test_description");
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        post.setUser(user);
        postRepository.save(post);

        Files file = new Files();
        file.setPath("somePath");
        file.setPostId(post.getId());
        fileRepository.save(file);

        post2 = new Post();
        post2.setDescription("test_description_2");
        post2.setCreated(LocalDateTime.of(2024, Month.JANUARY, 5, 12, 0, 0));
        post2.setUser(user);
        postRepository.save(post2);
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
    public void wenSaveNewPostsAndFindAll() {
        var expectedList = List.of(post, post2);
        assertThat(postRepository.findAll()).isEqualTo(expectedList);
    }

    @Test
    public void wenFindPostsByToDay() {
        var expectedList = List.of(post);
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
}