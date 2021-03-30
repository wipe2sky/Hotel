package com.kurtsevich.hotel.server.db_model;

import lombok.Data;

@Data
public class ServiceDB extends AEntityDB {
    private String name;
    private Double price;

    public ServiceDB(Integer id, String name, Double price) {
        setId(id);
        this.name = name;
        this.price = price;
    }

    public ServiceDB(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
