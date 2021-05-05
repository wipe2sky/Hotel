package com.kurtsevich.hotel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
public abstract class AEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false, nullable=false)
    @Getter
    @Setter
    private Integer id;

    @Override
    public String toString() {
        return "id=" + id;
    }
}
