package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class PostRepository {
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
        return crudRepository.query("from Post p"
                        + " left join fetch p.user"
                        + " left join fetch p.files"
                        + " left join fetch p.historyList", Post.class);
    }

    public List<Post> findByToday() {
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        return crudRepository.query("from Post p"
                        + " left join fetch p.user u"
                        + " left join fetch u.participates"
                        + " left join fetch p.files"
                        + " left join fetch p.historyList"
                        + " where p.created > :fnow", Post.class,
                Map.of("fnow", now));
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
                        + "left join fetch p.user.cars "
                        + "where model = :fmodel", Post.class,
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
