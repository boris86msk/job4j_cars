package ru.job4j.cars.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class EngineRepositoryTest {

    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static CrudRepository crudRepository = new CrudRepository(sf);

    private static EngineRepository engineRepository = new EngineRepository(crudRepository);

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @AfterEach
    public void wipeTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE Engine")
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
    public void wenSaveEngine() {
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
        int id = engine.getId();
        assertThat(engineRepository.findById(id)).isNotEmpty();
        assertThat(engineRepository.findById(id).get()).isEqualTo(engine);
    }

    @Test
    public void wenNeedFindAllEngine() {
        Engine engine1 = new Engine();
        Engine engine2 = new Engine();
        engine1.setName("111-18");
        engine2.setName("111-19");
        engineRepository.save(engine1);
        engineRepository.save(engine2);
        List<Engine> engineList = List.of(engine1, engine2);
        assertThat(engineRepository.findAllEngine()).isEqualTo(engineList);
    }

    @Test
    public void wenFindEngineById() {
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
        int id = engine.getId();
        assertThat(engineRepository.findById(id).get()).isEqualTo(engine);
        assertThat(engineRepository.findById(id + 1)).isEmpty();
    }

    @Test
    public void wenSaveEngineThenUpdateIt() {
        String secondName = "111-19";
        Engine engine = new Engine();
        engine.setName("111-18");
        engineRepository.save(engine);
        int id = engine.getId();
        engine.setName(secondName);
        engineRepository.update(engine);
        assertThat(engineRepository.findById(id).get().getName()).isEqualTo(secondName);
    }

    @Test
    public void wenNeedDeleteEngine() {
        Engine engine = new Engine();
        engine.setName("V8");
        engineRepository.save(engine);
        int id = engine.getId();
        engineRepository.delete(id);
        assertThat(engineRepository.findById(id)).isEmpty();
    }
  
}