package com.kurtsevich.hotel.server.db_model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
public class RoomDB extends AEntityDB {
    private Integer number;
    private Integer capacity;
    private Integer stars;
    private Double price;
    private String status;
    private Integer guestsInRoom;
    private Boolean isCleaning;

    public RoomDB(Integer id ,Integer number, Integer capacity, Integer stars, Double price, String status, Integer guestsInRoom, Boolean isCleaning) {
        setId(id);
        this.number = number;
        this.capacity = capacity;
        this.stars = stars;
        this.price = price;
        this.status = status;
        this.guestsInRoom = guestsInRoom;
        this.isCleaning = isCleaning;
    }

    public RoomDB(Integer number, Integer capacity, Integer stars, Double price) {
        this.number = number;
        this.capacity = capacity;
        this.stars = stars;
        this.price = price;
    }
}
