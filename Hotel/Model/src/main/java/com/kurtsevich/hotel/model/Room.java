package com.kurtsevich.hotel.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude="histories")
@Entity
@Table(name = "room")
public class Room extends AEntity {
    @Column(name = "number", unique = true)
    private Integer number;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "stars")
    private Integer stars;
    @Column(name = "price")
    private Double price;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.FREE;
    @Column(name = "guests_in_room")
    private Integer guestsInRoom = 0;
    @Column(name = "is_cleaning")
    private Boolean isCleaning = false;
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<History> histories;

    public Room() {
    }



    public Room(Integer number, Integer capacity, Integer stars, Double price) {
        this.number = number;
        this.capacity = capacity;
        this.stars = stars;
        this.price = price;
    }
}
