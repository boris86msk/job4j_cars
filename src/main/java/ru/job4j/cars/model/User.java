package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Car> cars = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id") }
    )
    private List<Post> participates = new ArrayList<>();

    /**
     * name - указывает на таблицу, где идет связь вторичных ключей.
     * joinColumns - определяет ключ родительского объекта. В данном примере User.id
     * inverseJoinColumns - определяет ключ объекта, который мы загружаем в родительский объект (Post).
     */

    public int getId() {
        return id;
    }
}
