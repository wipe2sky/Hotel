package com.api.service;

import com.model.Room;
import com.model.RoomStatus;
import com.util.comparators.ComparatorStatus;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    Room addRoom(Integer number, Integer capacity, Integer stars, Float price);

    void setCleaningStatus(Integer roomId, Boolean status);

    void setPrice(Integer roomId, Float price);

    List<Room> getSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus);

    List<Room> getAvailableAfterDate(LocalDate date);


    Integer getNumberOfFree();

    Room getInfo(Integer roomId);

    void setRepairStatus(Integer roomId, boolean bol);
}

