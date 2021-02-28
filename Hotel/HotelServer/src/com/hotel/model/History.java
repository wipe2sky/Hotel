package com.hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class History extends AEntity{
    private Room room;
    private Guest guest;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Float costOfLiving;
    private Float costOfService = 0F;
    private List<Service> services = new ArrayList<>();

    public History(Room room, Guest guest, LocalDate checkInDate, LocalDate checkOutDate) {
        setRoom(room);
        setGuest(guest);
        setCheckInDate(checkInDate);
        setCheckOutDate(checkOutDate);
        setCostOfLiving(ChronoUnit.DAYS.between(getCheckInDate(), getCheckOutDate()) * room.getPrice());
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Float getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(Float costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    public Float getCostOfService() {
        return costOfService;
    }

    public void setCostOfService(Float costOfService) {
        this.costOfService = costOfService;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "History{" +
                "Id = " + getId() +
                " room = " + room +
                ", guest=" + guest +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", services=" + services +
                '}' + "\n";
    }
}
