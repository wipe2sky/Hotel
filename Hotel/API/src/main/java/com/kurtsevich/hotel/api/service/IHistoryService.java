package com.kurtsevich.hotel.api.service;

import com.kurtsevich.hotel.dto.CheckInDto;
import com.kurtsevich.hotel.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.dto.HistoryDto;
import com.kurtsevich.hotel.dto.ServiceWithoutHistoriesDto;
import com.kurtsevich.hotel.model.Guest;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Room;

import java.util.List;

public interface IHistoryService {
    History addHistory(Room room, Guest guest, Integer daysStay);

    void checkIn(CheckInDto checkInDto);

    void checkOut(Integer guestId);

    Double getCostOfLiving(Integer guestId);

    List<HistoryDto> getGuestHistory(Integer id);

    List<GuestWithoutHistoriesDto> getLast3GuestInRoom(Integer roomId);

    List<HistoryDto> getAll();

    List<ServiceWithoutHistoriesDto> getListOfGuestService(Integer guestId);
}
