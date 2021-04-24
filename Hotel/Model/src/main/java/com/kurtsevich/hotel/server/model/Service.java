package com.kurtsevich.hotel.server.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@ToString(exclude="histories")
@Entity
@Table(name = "service")
public class Service extends AEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "history_service",
            joinColumns = { @JoinColumn(name = "service_id") },
            inverseJoinColumns = { @JoinColumn(name = "history_id") }
    )
    private List<History> histories;

    public Service() {
    }

    public Service(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
