package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipatesRepository {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public void save(int postId, int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            String sql = "INSERT INTO participates (user_id, post_id) VALUES (:userId, :postId)";
            session.createSQLQuery(sql)
                    .setParameter("userId", userId)
                    .setParameter("postId", postId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(int postId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            String sql = "DELETE FROM participates WHERE post_id = :postId";
            session.createSQLQuery(sql)
                    .setParameter("postId", postId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
