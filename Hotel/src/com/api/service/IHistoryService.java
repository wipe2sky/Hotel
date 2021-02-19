package com.api.service;

import com.model.Guest;
import com.model.History;
import com.model.Room;
import com.model.Service;

import java.time.LocalDate;
import java.util.List;

public interface IHistoryService {
    History addHistory(Room room, Guest guest, LocalDate checkInDate, LocalDate checkoutDate);
    void checkIn(Integer guestId, Integer roomId, LocalDate checkInDate, LocalDate checkoutDate);
    void checkOut(Integer guestId);
    Float getCostOfLiving(Integer guestId);

    List<History> getLast3GuestInRoom(Integer roomId);

    List<History> getAll();
    List<Service> getListOfGuestService(Integer guestId);

}
