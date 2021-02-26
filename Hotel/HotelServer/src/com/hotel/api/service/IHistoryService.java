package com.hotel.api.service;

import com.hotel.model.Guest;
import com.hotel.model.History;
import com.hotel.model.Room;
import com.hotel.model.Service;

import java.util.List;

public interface IHistoryService {
    History addHistory(Room room, Guest guest, Integer daysStay);
    void checkIn(Integer guestId, Integer roomId, Integer daysStay);
    void checkOut(Integer guestId);
    Float getCostOfLiving(Integer guestId);
    List<History> getGuestHistory (Integer id);
    List<History> getLast3GuestInRoom(Integer roomId);

    List<History> getAll();
    List<Service> getListOfGuestService(Integer guestId);

}
