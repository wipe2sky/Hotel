package com.kurtsevich.hotel.api.dao;

import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.model.Room;
import com.kurtsevich.hotel.SortStatus;

import java.util.List;

public interface IGuestDao extends GenericDao<Guest> {
    List<Guest> getSortBy(SortStatus sortStatus);

    Long getCountGuestInHotel();

    List<Guest> getAllGuestInHotel();

    List<Guest> getLast3GuestInRoom(Room room);


}
