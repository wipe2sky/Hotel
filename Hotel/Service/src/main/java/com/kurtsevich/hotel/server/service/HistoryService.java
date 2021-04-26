package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.service.IHistoryService;
import com.kurtsevich.hotel.server.dto.CheckInDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDto;
import com.kurtsevich.hotel.server.model.*;
import com.kurtsevich.hotel.server.util.GuestMapper;
import com.kurtsevich.hotel.server.util.HistoryMapper;
import com.kurtsevich.hotel.server.util.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HistoryService implements IHistoryService {
    private final IGuestDao guestDao;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;
    private final HistoryMapper historyMapper;
    private final GuestMapper guestMapper;
    private final ServiceMapper serviceMapper;


    @Override
    public History addHistory(Room room, Guest guest, Integer daysStay) {
        History history = new History(LocalDateTime.now(), LocalDateTime.now().plusDays(daysStay), room.getPrice() * daysStay, room, guest);
        historyDao.save(history);
        return history;
    }


    @Override
    public void checkIn(CheckInDto checkInDto) {
        Room room = roomDao.getById(checkInDto.getRoomId());
        Guest guest = guestDao.getById(checkInDto.getGuestId());
        if (room.getGuestsInRoom() < room.getCapacity()) {
            log.info("Check-in of the guest № {} to the room № {} for {} days", checkInDto.getGuestId(), checkInDto.getRoomId(), checkInDto.getDaysStay());
            guest.setCheckIn(true);
            guestDao.update(guest);
            room.setStatus(RoomStatus.BUSY);
            room.setGuestsInRoom(room.getGuestsInRoom() + 1);
            roomDao.update(room);
            addHistory(room, guest, checkInDto.getDaysStay());
        } else {
            log.info("Room {} busy.", checkInDto.getRoomId());
        }
    }

    @Override
    public void checkOut(Integer guestId) {
        Guest guest = guestDao.getById(guestId);
        History history = historyDao.getCurrentGuestHistories(guest);
        Room room = history.getRoom();

        log.info("Check-out of the guest № {} to the room № {}", guestId, room.getId());
        history.setCostOfLiving(ChronoUnit.DAYS.between(history.getCheckInDate(),
                LocalDateTime.now()) > 1
                ? ChronoUnit.DAYS.between(history.getCheckInDate(), LocalDateTime.now()) * room.getPrice() + history.getCostOfService()
                : room.getPrice() + history.getCostOfService());
        room.setGuestsInRoom(room.getGuestsInRoom() - 1);
        guest.setCheckIn(false);

        if (room.getGuestsInRoom() == 0) {
            room.setStatus(RoomStatus.FREE);
        }
        history.setCheckOutDate(LocalDateTime.now());
        history.setCurrent(false);
        historyDao.update(history);
        roomDao.update(room);
        guestDao.update(guest);
    }

    @Override
    public Double getCostOfLiving(Integer guestId) {
        Guest guest = guestDao.getById(guestId);
        History history = historyDao.getCurrentGuestHistories(guest);
        return history.getCostOfLiving();
    }

    @Override
    public List<GuestWithoutHistoriesDto> getLast3GuestInRoom(Integer roomId) {
        return guestDao.getLast3GuestInRoom(roomDao.getById(roomId)).stream()
                .map(guestMapper::guestToGuestWithoutHistoriesDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> getGuestHistory(Integer id) {
        return historyDao.getGuestHistories(guestDao.getById(id)).stream()
                .map(historyMapper::historyToHistoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoryDto> getAll() {
        return historyDao.getAll().stream()
                .map(historyMapper::historyToHistoryDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ServiceWithoutHistoriesDto> getListOfGuestService(Integer guestId) {
        return historyDao.getCurrentGuestHistories(guestDao.getById(guestId)).getServices().stream()
                .map(serviceMapper::serviceToServiceWithoutHistoriesDTO)
                .collect(Collectors.toList());
    }
}
