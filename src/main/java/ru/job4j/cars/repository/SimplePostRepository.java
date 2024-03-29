package ru.job4j.cars.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class SimplePostRepository implements PostRepository {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository;

    public SimplePostRepository(CrudRepository crudRepository) {
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
            List<Post> list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.user u"
                            + " left join fetch u.participates"
                            + " left join fetch p.car c"
                            + " left join fetch c.owners"
                            + " left join fetch p.file", Post.class)
                    .list();
            list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.historyList"
                            + " where p in :posts"
                            + " order by p.id desc", Post.class)
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
                            + " left join fetch p.file", Post.class)
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
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Post> list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.user"
                            + " left join fetch p.car c"
                            + " left join fetch p.file"
                            + " where p.file is not null", Post.class)
                    .list();
            list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.historyList"
                            + " where p in :posts"
                            + " order by p.id desc", Post.class)
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

    public List<Post> findByBrand(String brand) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Post> list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.user"
                            + " left join fetch p.car c"
                            + " left join fetch p.file"
                            + " where c.brand.name = :brand", Post.class)
                    .setParameter("brand", brand)
                    .list();
            list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.historyList"
                            + " where p in :posts"
                            + " order by p.id desc", Post.class)
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

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "from Post p "
                        + "left join fetch p.user "
                        + "left join fetch p.file "
                        + "left join fetch p.car c "
                        + "left join fetch c.owners "
                        + "where p.id = :fId", Post.class,
                Map.of("fId", id)
        );
    }

    public List<Post> findByUser(int userId) {
        return crudRepository.query("from Post p "
                        + "left join fetch p.user u "
                        + "left join fetch p.car c "
                        + "where u.id = :userId", Post.class,
                Map.of("userId", userId));
    }

    public List<Post> findByMaxPrice(int price) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Post> list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.user"
                            + " left join fetch p.car"
                            + " left join fetch p.file"
                            + " where p.price < :price", Post.class)
                    .setParameter("price", price)
                    .list();
            list = session.createQuery("select distinct p from Post p"
                            + " left join fetch p.historyList"
                            + " where p in :posts"
                            + " order by p.id desc", Post.class)
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

    public void updateById(int id, int price) {
        crudRepository.run(
                "UPDATE Post p"
                        + " SET p.price = :newPrice"
                        + " WHERE p.id = :postId",
                Map.of("newPrice", price, "postId", id)
        );
    }

    public void sold(int postId) {
        crudRepository.run("UPDATE Post p"
                        + " SET p.status = false"
                        + " WHERE p.id = :postId",
                Map.of("postId", postId)
        );
    }

    public String delete(int postId, int fileId) {
        Session session = sf.openSession();
        String path = null;
        try {
            session.beginTransaction();
            path = session.createQuery("from File where id = :fid", File.class)
                    .setParameterList("fid", Collections.singleton(fileId))
                    .uniqueResult()
                    .getPath();
            session.createNativeQuery("delete from participates where post_id = :postId")
                    .setParameter("postId", postId)
                    .executeUpdate();
            session.createNativeQuery("delete from price_history where post_id = :postId")
                    .setParameter("postId", postId)
                    .executeUpdate();
            session.createNativeQuery("delete from auto_post where id = :postId")
                    .setParameter("postId", postId)
                    .executeUpdate();
            session.createNativeQuery("delete from files where id = :fileId")
                    .setParameter("fileId", fileId)
                    .executeUpdate();
            session.getTransaction().commit();
            return path;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return path;
    }
}
