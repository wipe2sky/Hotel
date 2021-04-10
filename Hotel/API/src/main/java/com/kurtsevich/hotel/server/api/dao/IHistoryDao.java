package com.kurtsevich.hotel.server.api.dao;

import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface IHistoryDao extends GenericDao<History> {
    List<History> getByGuest(Guest guest);
//    List<History> getByRoom(Room room);

    List<Room> getAvailableAfterDate(LocalDateTime date);

    List<Guest> getLast3GuestInRoom(Room room);

}
