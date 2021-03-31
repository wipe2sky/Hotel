package com.kurtsevich.hotel.server.model;

import lombok.Data;

@Data
public class Service extends AEntity {
    private String name;
    private Double price;

    public Service(Integer id, String name, Double price) {
        setId(id);
        this.name = name;
        this.price = price;
    }

    public Service(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
