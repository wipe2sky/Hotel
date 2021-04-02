package com.kurtsevich.hotel.server.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class History extends AEntity {
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Double costOfLiving;
    private Double costOfService;
    private Room room;
    private Guest guest;
    private List<Service> services;

    public History(LocalDateTime checkInDate, LocalDateTime checkOutDate, Double costOfLiving, Room room, Guest guest) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.costOfLiving = costOfLiving;
        this.room = room;
        this.guest = guest;
    }

    public History(Integer id, LocalDateTime checkInDate, LocalDateTime checkOutDate, Double costOfLiving, Double costOfService, Room room, Guest guest, List<Service> services) {
        setId(id);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.costOfLiving = costOfLiving;
        this.costOfService = costOfService;
        this.room = room;
        this.guest = guest;
        this.services = services;
    }
}
