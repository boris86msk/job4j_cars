package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final CrudRepository crudRepository;

    public UserRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
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
     *
     * @param login login.
     * @param pass
     * @return Optional or user.
     */
    public Optional<User> findByLoginAndPassword(String login, String pass) {
        return crudRepository.optional(
                "from User where login = :flogin"
                        + " and password = :fPass", User.class,
                Map.of("flogin", login, "fPass", pass)
        );
    }
}
