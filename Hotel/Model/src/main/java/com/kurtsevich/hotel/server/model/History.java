package com.kurtsevich.hotel.server.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "history")
public class History extends AEntity {
    @Column(name = "check_in_date")
    private LocalDateTime checkInDate;
    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;
    @Column(name = "cost_of_living")
    private Double costOfLiving;
    @Column(name = "cost_of_service")
    private Double costOfService = 0d;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room", nullable=false)
    private Room room;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="guest", nullable=false)
    private Guest guest;
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "history_service",
            joinColumns = { @JoinColumn(name = "history_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_id") }
    )
    private List<Service> services;

    public History() {
    }

    public History(LocalDateTime checkInDate, LocalDateTime checkOutDate, Double costOfLiving, Room room, Guest guest) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.costOfLiving = costOfLiving;
        this.room = room;
        this.guest = guest;
    }
}
