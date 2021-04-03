package com.kurtsevich.hotel.server.model;

import lombok.Data;

@Data
public class Room extends AEntity {
    private Integer number;
    private Integer capacity;
    private Integer stars;
    private Double price;
    private RoomStatus status;
    private Integer guestsInRoom;
    private Boolean isCleaning;

    public Room(Integer id , Integer number, Integer capacity, Integer stars, Double price, RoomStatus status, Integer guestsInRoom, Boolean isCleaning) {
        setId(id);
        this.number = number;
        this.capacity = capacity;
        this.stars = stars;
        this.price = price;
        this.status = status;
        this.guestsInRoom = guestsInRoom;
        this.isCleaning = isCleaning;
    }

    public Room(Integer number, Integer capacity, Integer stars, Double price) {
        this.number = number;
        this.capacity = capacity;
        this.stars = stars;
        this.price = price;
    }
}
