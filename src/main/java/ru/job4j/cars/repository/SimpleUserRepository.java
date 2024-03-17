package ru.job4j.cars.repository;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SimpleUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    public SimpleUserRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Optional<User> create(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    public void delete(int userId) {
        crudRepository.run(
                "delete from User where id = :fId",
                Map.of("fId", userId)
        );
    }

    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User u"
                        + " left join fetch u.participates"
                        + " order by u.id", User.class
        );
    }

    public List<User> findAllOrderByName() {
        return crudRepository.query(
                "from User u"
                        + " left join fetch u.participates"
                        + " order by u.name asc", User.class
        );
    }

    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User u"
                        + " left join fetch u.participates"
                        + " where u.id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User where login like :fkey", User.class,
                Map.of("fkey", "%" + key + "%")
        );
    }

    public Optional<User> findByLoginAndPassword(String login, String pass) {
        return crudRepository.optional(
                "from User where login = :flogin"
                        + " and password = :fPass", User.class,
                Map.of("flogin", login, "fPass", pass)
        );
    }
}
