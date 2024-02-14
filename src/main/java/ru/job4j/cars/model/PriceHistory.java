package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PriceHistory implements Comparable<PriceHistory> {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "before")
    private int before;
    @Column(name = "after")
    private int after;
    @Column(name = "created")
    private LocalDateTime created;

    @Override
    public int compareTo(PriceHistory o) {
        int res = 0;
        if(this.created.isBefore(o.created)) {
            res = -1;
        } else if(this.created.isAfter(o.created)) {
            res = 1;
        }
        return res;
    }
}
