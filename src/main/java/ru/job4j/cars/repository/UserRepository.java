package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final CrudRepository crudRepository;
    private final SessionFactory sf;

    public UserRepository(CrudRepository crudRepository, SessionFactory sf) {
        this.crudRepository = crudRepository;
        this.sf = sf;
    }

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public Optional<User> create(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {

        crudRepository.run(session -> session.merge(user));

//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createQuery("UPDATE User SET password = :pass WHERE id = :fId")
//                    .setParameter("pass", user.getPassword())
//                    .setParameter("fId", user.getId())
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.getTransaction().rollback();
//        } finally {
//            session.close();
//        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        crudRepository.run(
                "delete from User where id = :fId",
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User order by id", User.class
        );
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User where login like :fkey", User.class,
                Map.of("fkey", "%" + key + "%")
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :flogin", User.class,
                Map.of("flogin", login)
        );
    }
}
