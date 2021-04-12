package com.kurtsevich.hotel.server.api.dao;

import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.util.SortStatus;

import java.util.List;

public interface IGuestDao extends GenericDao<Guest> {
    List<Guest> getSortBy(SortStatus sortStatus);

    Long getCountGuestInHotel();

    List<Guest> getAllGuestInHotel();

    List<Guest> getLast3GuestInRoom(Room room);


}
