package com.kurtsevich.hotel.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest extends AEntity {
    private String firstName;
    private String lastName;
    private Room room;
    private History lastHistory;
    private boolean isCheckIn;
    private List<Service> services = new ArrayList<>();
    private List<History> histories = new ArrayList<>();


    public Guest(String lastName, String firstName) {
        setLastName(lastName);
        setFirstName(firstName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public History getLastHistory() {
        return lastHistory;
    }

    public void setLastHistory(History lastHistory) {
        this.lastHistory = lastHistory;
    }

    public boolean isCheckIn() {
        return isCheckIn;
    }

    public void setCheckIn(boolean checkIn) {
        isCheckIn = checkIn;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }


    @Override
    public String toString() {
        return "Guest{" +
                "id = " + getId() +
                ", lastName = '" + lastName + '\'' +
                ", firstName = '" + firstName + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(firstName, guest.firstName) && Objects.equals(lastName, guest.lastName) && Objects.equals(getId(), guest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, getId());
    }
}
