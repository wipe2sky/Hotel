package com.hotel.api.service;

import com.hotel.model.History;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.util.comparators.ComparatorStatus;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Room addRoom(Integer number, Integer capacity, Integer stars, Float price);

    void deleteRoom(Integer id);

    void setCleaningStatus(Integer roomId, Boolean status);

    void changePrice(Integer roomId, Float price);

    List<Room> getSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus);

    List<Room> getAvailableAfterDate(LocalDate date);


    Integer getNumberOfFree();

    Room getInfo(Integer roomId);

    List<History> getRoomHistory(Integer roomId);

    void setRepairStatus(Integer roomId, boolean bol);

    List<Room> getAll();


}

