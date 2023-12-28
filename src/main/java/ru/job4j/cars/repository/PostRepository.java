package ru.job4j.cars.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {
    private final CrudRepository crudRepository;

    public PostRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<Post> findAll() {
        return crudRepository.query("from Post", Post.class);
    }
    public List<Post> findByToday() {
        LocalDate now = LocalDate.now();
        return crudRepository.query("from Post where created = :fnow", Post.class,
                Map.of("fnow", now));
    }

    public List<Post> findAllWhereFilesNotNull() {
        return crudRepository.query("from Post p"
                + " left join fetch p.files where size(p.files) > 0", Post.class);
    }

    public List<Post> findByModel(String model) {
        return crudRepository.query("from Post p "
                        + "left join fetch p.user "
                        + "left join fetch p.user.cars "
                        + "where model = :fmodel", Post.class,
                Map.of("fmodel", model));
    }

}
