package com.kurtsevich.hotel.server.api.service;

import com.kurtsevich.hotel.server.dto.CheckInDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDTO;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;

import java.util.List;

public interface IHistoryService {
    History addHistory(Room room, Guest guest, Integer daysStay);

    void checkIn(CheckInDto checkInDto);

    void checkOut(Integer guestId);

    Double getCostOfLiving(Integer guestId);

    List<HistoryDto> getGuestHistory(Integer id);

    List<GuestWithoutHistoriesDto> getLast3GuestInRoom(Integer roomId);

    List<HistoryDto> getAll();

    List<ServiceWithoutHistoriesDTO> getListOfGuestService(Integer guestId);
}
