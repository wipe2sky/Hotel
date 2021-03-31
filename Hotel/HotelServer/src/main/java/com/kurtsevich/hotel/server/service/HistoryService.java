package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.service.IHistoryService;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.*;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.comparators.HistoryDateOutComparator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class HistoryService implements IHistoryService {
    private static final Logger logger = new Logger(HistoryService.class.getName());
    private final IGuestDao guestDao;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;

    @InjectByType
    public HistoryService(IGuestDao guestDao, IRoomDao roomDao, IHistoryDao historyDao) {
        this.guestDao = guestDao;
        this.roomDao = roomDao;
        this.historyDao = historyDao;
    }


    @Override
    public History addHistory(Room room, Guest guest, Integer daysStay) {
        History history = new History(LocalDateTime.now(), LocalDateTime.now().plusDays(daysStay), room.getPrice() * daysStay, room, guest);
        historyDao.save(history);
        return history;
    }


    @Override
    public void checkIn(Integer guestId, Integer roomId, Integer daysStay) {
        try {
            Room room = roomDao.getById(roomId);
            Guest guest = guestDao.getById(guestId);
            if (room.getGuestsInRoom() < room.getCapacity()) {
                logger.log(Logger.Level.INFO, String.format("Check-in of the guest № %d to the room № %d for %d days", guestId, roomId, daysStay));
                guest.setCheckIn(true);
                guestDao.update(guest);
                room.setStatus(RoomStatus.BUSY);
                room.setGuestsInRoom(room.getGuestsInRoom() + 1);
                roomDao.update(room);
                addHistory(room, guest, daysStay);
            } else logger.log(Logger.Level.INFO, "Room " + roomId + " busy.");
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Chek-in failed.", e);
            throw new ServiceException("Chek-in failed.", e);
        }
    }

    @Override
    public void checkOut(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getByGuest(guest).get(0);
            Room room = history.getRoom();

            logger.log(Logger.Level.INFO, String.format("Check-out of the guest № %d to the room № %d", guestId, room.getId()));

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
            historyDao.update(history);
            roomDao.update(room);
            guestDao.update(guest);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Chek-out failed.", e);
            throw new ServiceException("Chek-out failed.", e);
        }
    }

    @Override
    public Double getCostOfLiving(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getByGuest(guest).get(0);

            return history.getCostOfLiving();
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get cost of living failed.", e);
            throw new ServiceException("Get cost of living failed.", e);
        }
    }

    @Override
    public List<History> getLast3GuestInRoom(Integer roomId) {
        return historyDao.getAll().stream()
                .filter(history -> history.getRoom().getId().equals(roomId))
                .limit(3)
                .sorted(Collections.reverseOrder(new HistoryDateOutComparator()))
                .collect(Collectors.toList());
    }

    @Override
    public List<History> getGuestHistory(Integer id) {
        return historyDao.getAll().stream()
                .filter(history -> history.getGuest().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<History> getAll() {
        return historyDao.getAll().stream()
                .sorted(Comparator.comparing(AEntity::getId).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public List<Service> getListOfGuestService(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getByGuest(guest).get(0);
            return history.getServices();
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get list of guest service failed.", e);
            throw new ServiceException("Get list of guest service failed.", e);
        }
    }

    @Override
    public List<History> getByGuestId(Integer guestId) {

        return historyDao.getByGuest(guestDao.getById(guestId));
    }

    @Override
    public List<History> getByRoomId(Integer roomId) {
        return historyDao.getByRoom(roomDao.getById(roomId));
    }
}
