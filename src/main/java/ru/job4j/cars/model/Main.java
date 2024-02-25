package ru.job4j.cars.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Optional;

public class Main {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
//        Pasport pasport = new Pasport();
//        pasport.setName("83524g9");
//        Human human = new Human();
//        human.setModel("vtorodvssby");
//        human.setPasport(pasport);
//
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.save(human);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.getTransaction().rollback();
//        } finally {
//            session.close();
//        }

        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE Human WHERE id = :id")
                            .setParameter("id", 2)
                                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }
}
