package com.kurtsevich.hotel.server.api.service;

import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {

    Room addRoom(Integer number, Integer capacity, Integer stars, Double price);

    void deleteRoom(Integer id);

    void setCleaningStatus(Integer roomId, Boolean status);

    void changePrice(Integer roomId, Double price);

    List<Room> getSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus);

    List<Room> getAvailableAfterDate(LocalDateTime date);


    Integer getNumberOfFree();

    Room getInfo(Integer roomId);

    List<History> getRoomHistory(Integer roomId);

    void setRepairStatus(Integer roomId, boolean bol);

    List<Room> getAll();


}

