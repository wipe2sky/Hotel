package com.kurtsevich.hotel.server.api.service;

import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.Service;

import java.util.List;

public interface IHistoryService {
    History addHistory(Room room, Guest guest, Integer daysStay);
    void checkIn(Integer guestId, Integer roomId, Integer daysStay);
    void checkOut(Integer guestId);
    Double getCostOfLiving(Integer guestId);
    List<History> getGuestHistory (Integer id);
    List<Guest> getLast3GuestInRoom(Integer roomId);

    List<History> getAll();

    List<Service> getListOfGuestService(Integer guestId);
//    public List<History> getByGuestId(Integer guestId);
//    public List<History> getByRoomId(Integer roomId);
}
