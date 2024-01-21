package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car")
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "body_id")
    private BodyType bodyType;

    @ManyToMany
    @JoinTable(
            name = "history_owners",
            joinColumns = { @JoinColumn(name = "car_id") },
            inverseJoinColumns = { @JoinColumn(name = "owner_id") }
    )
    private Set<Owner> owners = new HashSet<>();
}
