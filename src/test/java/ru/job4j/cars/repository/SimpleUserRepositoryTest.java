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
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleUserRepositoryTest {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static SimpleUserRepository simpleUserRepository = new SimpleUserRepository(crudRepository);

    private static User user;
    private static User user2;
    private static int userId;
    private static int user2Id;

    @BeforeEach
    public void createAndSaveUserToDb() {
        user = new User();
        user.setLogin("myLogin");
        user.setPassword("1111");
        user.setName("Иванов Иван Ивыныч");
        simpleUserRepository.create(user);
        userId = user.getId();

        user2 = new User();
        user2.setLogin("BobMarley");
        user2.setPassword("1234");
        user2.setName("Петров Петр Петрович");
        simpleUserRepository.create(user2);
        user2Id = user2.getId();
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
    public void wenSaveNewUserAndFindHisById() {
        assertThat(simpleUserRepository.findById(userId)).isNotEmpty();
        assertThat(simpleUserRepository.findById(userId).get()).isEqualTo(user);
    }

    @Test
    public void wenNeedUpdateUser() {
        String newPass = "2222";
        user.setPassword(newPass);
        simpleUserRepository.update(user);
        assertThat(simpleUserRepository.findById(userId)).isNotEmpty();
        assertThat(simpleUserRepository.findById(userId).get().getPassword()).isEqualTo(newPass);
    }

    @Test
    public void wenWeDeleteUserById() {
        simpleUserRepository.delete(userId);
        assertThat(simpleUserRepository.findById(userId)).isEmpty();
    }

    @Test
    public void wenFindUserByLogin() {
        String findLogin = "myLogin";
        String findPass = "1111";
        var findUser = simpleUserRepository.findByLoginAndPassword(findLogin, findPass);
        assertThat(findUser.get().getLogin()).isEqualTo(findLogin);
        assertThat(findUser.get().getPassword()).isEqualTo(findPass);
    }

    @Test
    public void wenFindUserByLikeLogin() {
        String likeLogin = "my";
        var findUsers = simpleUserRepository.findByLikeLogin(likeLogin);
        assertThat(findUsers).contains(user);
        assertThat(findUsers).doesNotContain(user2);
    }

    @Test
    public void wenNeedFindAllUsersOrderById() {
        List<User> adcList = List.of(user, user2);
        List<User> descList = List.of(user2, user);
        assertThat(simpleUserRepository.findAllOrderById()).isEqualTo(adcList);
        assertThat(simpleUserRepository.findAllOrderById()).isNotEqualTo(descList);
    }

    @Test
    public void wenNeedFindAllUsersOrderByName() {
        List<User> adcList = List.of(user, user2);
        List<User> descList = List.of(user2, user);
        assertThat(simpleUserRepository.findAllOrderByName()).isEqualTo(adcList);
        assertThat(simpleUserRepository.findAllOrderByName()).isNotEqualTo(descList);
    }

}