package com.kurtsevich.hotel.api.dao;

import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Room;

import java.util.List;

public interface IHistoryDao extends GenericDao<History> {

    List<History> getGuestHistories(Guest guest);

    History getCurrentGuestHistories(Guest guest);


    List<History> getRoomHistories(Room room);



}
