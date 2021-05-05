package com.kurtsevich.hotel.api.dao;

import com.kurtsevich.hotel.model.Room;
import com.kurtsevich.hotel.model.RoomStatus;
import com.kurtsevich.hotel.SortStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomDao extends GenericDao<Room> {
    List<Room> getSortBy(SortStatus sortStatus, RoomStatus roomStatus);

    Integer getNumberOfFree();

    List<Room> getAvailableAfterDate(LocalDateTime date);

}
