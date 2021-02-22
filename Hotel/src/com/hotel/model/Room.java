package com.hotel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room extends AEntity{
    private Integer number;
    private Integer capacity;
    private Integer stars;
    private Integer guestsInRoom = 0;
    private Float price;
    private RoomStatus status = RoomStatus.FREE;
    private Boolean isCleaning = false;
    private History lastHistory;
    private List<Guest> guests = new ArrayList<>();
    private List<History> histories = new ArrayList<>();

    public Room (Integer number, Integer capacity, Integer stars, Float price){
        setNumber(number);
        setCapacity(capacity);
        setStars(stars);
        setPrice(price);
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setGuestsInRoom(Integer guestsInRoom) {
        this.guestsInRoom = guestsInRoom;
    }

    public Integer getGuestsInRoom() {
        return guestsInRoom;
    }

    public void incrementNumberOfGuests() {
        this.guestsInRoom++;
    }

    public void decrementNumberOfGuests() {
        this.guestsInRoom--;
    }

    //
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus roomStatus) {
        this.status = roomStatus;
    }


    public Boolean getIsCleaning() {
        return isCleaning;
    }

    public void setIsCleaning(Boolean isCleaning) {
        this.isCleaning = isCleaning;
    }

    public History getLastHistory() {
        return lastHistory;
    }

    public void setLastHistory(History lastHistory) {
        this.lastHistory = lastHistory;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return number.equals(room.number) && capacity.equals(room.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id= " + getId() +
                ", number=" + getNumber() +
                ", capacity=" + getCapacity() +
                ", stars=" + getStars() +
                ", price=" + getPrice() +
                ", Status=" + getStatus() +
                ", isCleaning=" + getIsCleaning() +
                ", guestsInRoom=" + getGuestsInRoom() +
                '}' + "\n";
    }
}
