package ru.job4j.cars.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PostRepository {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository;

    public PostRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Optional<Post> save(Post post) {
        try {
            crudRepository.run(session -> session.persist(post));
            return Optional.of(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Post> findAll() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Post> list = session.createQuery("from Post p"
                            + " left join fetch p.user"
                            + " left join fetch p.car"
                            + " left join fetch p.files", Post.class)
                    .list();
            list = session.createQuery("from Post p"
                            + " left join fetch p.historyList"
                            + " where p in :posts", Post.class)
                    .setParameterList("posts", list)
                    .list();
            session.getTransaction().commit();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Post> findByToday() {
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Post> list = session.createQuery("from Post p"
                            + " left join fetch p.user"
                            + " left join fetch p.car"
                            + " left join fetch p.files", Post.class)
                    .list();
            list = session.createQuery("from Post p"
                            + " left join fetch p.historyList"
                            + " where p in :posts and p.created > :fnow", Post.class)
                    .setParameterList("posts", list)
                    .setParameterList("fnow", Collections.singleton(now))
                    .list();
            session.getTransaction().commit();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Post> findAllWhereFilesNotNull() {
        return crudRepository.query("from Post p"
                + " left join fetch p.files"
                + " left join fetch p.user"
                + " where size(p.files) > 0", Post.class);
    }

    public List<Post> findByModel(String model) {
        return crudRepository.query("from Post p "
                        + "left join fetch p.user "
                        + "left join fetch p.car c "
                        + "where c.model = :fmodel", Post.class,
                Map.of("fmodel", model));
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "from Post p "
                        + "left join fetch p.user "
                        + "left join fetch p.files "
                        + "where p.id = :fId", Post.class,
                Map.of("fId", id)
        );
    }
}
