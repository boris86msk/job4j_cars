package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PriceHistoryRepository {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private static SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository;

    public PriceHistoryRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Optional<PriceHistory> save(PriceHistory priceHistory) {
        try {
            crudRepository.run(session -> session.persist(priceHistory));
            return Optional.of(priceHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<PriceHistory> findAllByPostId(int postId) {
        return crudRepository.query("from PriceHistory"
                + " where post_id = :id"
                + " order by created", PriceHistory.class,
                Map.of("id", postId));
    }
}
