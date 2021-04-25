package com.kurtsevich.hotel.server.api.dao;

import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;

import java.util.List;

public interface IHistoryDao extends GenericDao<History> {

    List<History> getGuestHistories(Guest guest);

    History getCurrentGuestHistories(Guest guest);


    List<History> getRoomHistories(Room room);



}
