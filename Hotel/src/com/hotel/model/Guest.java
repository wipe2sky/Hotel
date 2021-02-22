package com.hotel.model;

import java.util.ArrayList;
import java.util.List;

public class Guest extends AEntity {
    private String firstName;
    private String lastName;
    private Room room;
    private History lastHistory;
    private boolean isCheckIn;
    private List<Service> services = new ArrayList<>();
    private List<History> histories = new ArrayList<>();


    public Guest(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
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
                "id= " + getId() +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", room=" + room +
                ", services=" + services +
                '}' + "\n";
    }
}
