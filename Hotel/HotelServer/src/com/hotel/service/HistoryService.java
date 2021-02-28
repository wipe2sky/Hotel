package com.hotel.service;

import com.hotel.api.dao.IGuestDao;
import com.hotel.api.dao.IHistoryDao;
import com.hotel.api.dao.IRoomDao;
import com.hotel.api.service.IHistoryService;
import com.hotel.dao.GuestDao;
import com.hotel.dao.HistoryDao;
import com.hotel.dao.RoomDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.*;
import com.hotel.util.generator.IdGenerator;
import com.hotel.util.comparators.HistoryDateOutComparator;
import com.hotel.util.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryService implements IHistoryService {
    private static final Logger logger = new Logger(HistoryService.class.getName());

    private static HistoryService instance;
    private final IGuestDao guestDao;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;

    private HistoryService() {
        this.guestDao = GuestDao.getInstance();
        this.roomDao = RoomDao.getInstance();
        this.historyDao = HistoryDao.getInstance();
    }
    public static HistoryService getInstance() {
        if(instance == null) instance = new HistoryService();
        return instance;
    }

    @Override
    public History addHistory(Room room, Guest guest, Integer daysStay) {
        History history = new History(room, guest, LocalDate.now(), LocalDate.now().plusDays(daysStay));
        history.setId(IdGenerator.getInstance().generateHistoryId());
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
                History history = addHistory(room, guest, daysStay);
                guest.setHistories(historyDao.getAll());
                guest.setRoom(room);
                guest.setCheckIn(true);
                guest.setLastHistory(history);
                guestDao.update(guest);
                room.getGuests().add(guest);
                room.setStatus(RoomStatus.BUSY);
                room.incrementNumberOfGuests();
                room.setLastHistory(history);
                room.getHistories().add(history);
                roomDao.update(room);
            } else logger.log(Logger.Level.INFO, "Room " + roomId + " busy.");
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Chek-in failed.");
            throw new ServiceException("Chek-in failed.");
        }
    }

    @Override
    public void checkOut(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            Room room = roomDao.getById(guest.getRoom().getId());
            History history = historyDao.getById(guest.getLastHistory().getId());

            logger.log(Logger.Level.INFO, String.format("Check-out of the guest № %d to the room № %d", guestId, room.getId()));

            history.setCostOfLiving(ChronoUnit.DAYS.between(history.getCheckInDate(),
                    LocalDate.now()) > 1
                    ?  ChronoUnit.DAYS.between(history.getCheckInDate(), LocalDate.now()) * room.getPrice()
                    : room.getPrice());
            room.getGuests().remove(guest);
            room.decrementNumberOfGuests();
            guest.setRoom(null);
            guest.setCheckIn(false);
            guest.getLastHistory().setCheckOutDate(LocalDate.now());

            if (room.getGuests().isEmpty()) {
                room.setStatus(RoomStatus.FREE);
            }

            guest.getServices().clear();

            historyDao.update(history);
            roomDao.update(room);
            guestDao.update(guest);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Chek-out failed.");
            throw new ServiceException("Chek-out failed.");
        }
    }

    @Override
    public Float getCostOfLiving(Integer guestId) {
        try {
            Guest guest = guestDao.getById(guestId);
            return guest.getLastHistory().getCostOfLiving() + guest.getLastHistory().getCostOfService();
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get cost of living failed.");
            throw new ServiceException("Get cost of living failed.");
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
            return guestDao.getById(guestId).getServices();
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get list of guest service failed.");
            throw new ServiceException("Get list of guest service failed.");
        }
    }
}
