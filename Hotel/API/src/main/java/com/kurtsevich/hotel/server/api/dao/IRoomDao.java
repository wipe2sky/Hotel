package com.kurtsevich.hotel.server.api.dao;

import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.SortStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomDao extends GenericDao<Room> {
    List<Room> getSortBy(SortStatus sortStatus, RoomStatus roomStatus);

    Integer getNumberOfFree();

    List<Room> getAvailableAfterDate(LocalDateTime date);

}
