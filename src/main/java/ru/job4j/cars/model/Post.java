package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_post")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> historyList = new ArrayList<>();
}
/**
 * при связи OneToMany в @JoinColumn указываем имя столбца той таблицы, которая
 * "Many" и в которой он является foreign key
 *
 * при связи ManyToOne в @JoinColumn так же указываем имя столбца который "Many"
 * и является foreign key, (это будет текущая entity)
 */

