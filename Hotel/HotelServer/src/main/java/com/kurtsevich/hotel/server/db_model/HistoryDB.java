package com.kurtsevich.hotel.server.db_model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HistoryDB extends AEntityDB {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double costOfLiving;
    private Double costOfService;
    private RoomDB room;
    private GuestDB guest;
    private List<ServiceDB> services;

    public HistoryDB(LocalDate checkInDate, LocalDate checkOutDate, Double costOfLiving, RoomDB room, GuestDB guest) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.costOfLiving = costOfLiving;
        this.room = room;
        this.guest = guest;
    }

    public HistoryDB(Integer id, LocalDate checkInDate, LocalDate checkOutDate, Double costOfLiving, Double costOfService, RoomDB room, GuestDB guest, List<ServiceDB> services) {
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
