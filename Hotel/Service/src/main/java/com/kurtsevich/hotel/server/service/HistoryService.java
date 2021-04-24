package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IHistoryService;
import com.kurtsevich.hotel.server.dto.CheckInDto;
import com.kurtsevich.hotel.server.dto.GuestWithoutHistoriesDto;
import com.kurtsevich.hotel.server.dto.HistoryDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDTO;
import com.kurtsevich.hotel.server.model.*;
import com.kurtsevich.hotel.server.util.GuestMapper;
import com.kurtsevich.hotel.server.util.HistoryMapper;
import com.kurtsevich.hotel.server.util.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class HistoryService implements IHistoryService {
    private final IGuestDao guestDao;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;


    @Override
    public History addHistory(Room room, Guest guest, Integer daysStay) {
        History history = new History(LocalDateTime.now(), LocalDateTime.now().plusDays(daysStay), room.getPrice() * daysStay, room, guest);
        historyDao.save(history);
        return history;
    }


    @Override
    public void checkIn(CheckInDto checkInDto) {
        try {
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

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Chek-in failed.");
        }
    }

    @Override
    public void checkOut(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getGuestHistories(guest).get(0);
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
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Chek-out failed.");
        }
    }

    @Override
    public Double getCostOfLiving(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getGuestHistories(guest).get(0);
            return history.getCostOfLiving();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Guest history not found.");
        }
    }

    @Override
    public List<GuestWithoutHistoriesDto> getLast3GuestInRoom(Integer roomId) {
        try {
            List<Guest> guests = guestDao.getLast3GuestInRoom(roomDao.getById(roomId));
            List<GuestWithoutHistoriesDto> guestsDto = new ArrayList<>();
            guests.forEach(guest -> guestsDto.add(GuestMapper.INSTANCE.guestToGuestWithoutHistoriesDto(guest)));
            return guestsDto;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get guests in Room failed.");
        }
    }

    @Override
    public List<HistoryDto> getGuestHistory(Integer id) {
        try {
            List<History> histories = historyDao.getGuestHistories(guestDao.getById(id));
            List<HistoryDto> historiesDto = new ArrayList<>();
            histories.forEach(history -> historiesDto.add(HistoryMapper.INSTANCE.historyToHistoryDto(history)));
            return historiesDto;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get guests history failed.");
        }
    }

    @Override
    public List<HistoryDto> getAll() {
        try {
            List<History> histories = historyDao.getAll();
            List<HistoryDto> historiesDto = new ArrayList<>();
            histories.forEach(history -> historiesDto.add(HistoryMapper.INSTANCE.historyToHistoryDto(history)));
            return historiesDto;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get guests failed.");
        }
    }


    @Override
    public List<ServiceWithoutHistoriesDTO> getListOfGuestService(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getGuestHistories(guest).get(0);

            List<Service> services = history.getServices();
            List<ServiceWithoutHistoriesDTO> servicesDTO = new ArrayList<>();
            services.forEach(service -> servicesDTO.add(ServiceMapper.INSTANCE.serviceToServiceWithoutHistoriesDTO(service)));
            return servicesDTO;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get list of guest service failed.");
        }
    }
}
