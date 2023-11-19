package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return user;
        } catch (ConstraintViolationException e) {
            System.out.println(e.getSQLException().getLocalizedMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("UPDATE User SET password = :pass WHERE id = :fId")
                    .setParameter("pass", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<User> fromUser = session.createQuery("from User ORDER BY id", User.class).list();
            session.getTransaction().commit();
            return fromUser;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where id = :fuserId", User.class);
            query.setParameter("fuserId", userId);
            Optional<User> user = query.uniqueResultOptional();
            session.getTransaction().commit();
            return user;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<User> fromUser = session.createQuery("from User where login like :fkey", User.class)
                    .setParameter("fkey", "%" + key + "%")
                    .list();
            session.getTransaction().commit();
            return fromUser;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return List.of();
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where login = :flogin", User.class);
            query.setParameter("flogin", login);
            Optional<User> user = query.uniqueResultOptional();
            session.getTransaction().commit();
            return user;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }
}
