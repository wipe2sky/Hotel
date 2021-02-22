package com.hotel.service;

import com.hotel.api.dao.IGuestDao;
import com.hotel.api.dao.IHistoryDao;
import com.hotel.api.dao.IRoomDao;
import com.hotel.api.service.IHistoryService;
import com.hotel.dao.GuestDao;
import com.hotel.dao.HistoryDao;
import com.hotel.dao.RoomDao;
import com.hotel.model.*;
import com.hotel.util.IdGenerator;
import com.hotel.util.comparators.HistoryDateOutComparator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HistoryService implements IHistoryService {
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
//        return Objects.requireNonNullElse(instance, new HistoryService());
    }
    @Override
    public History addHistory(Room room, Guest guest, LocalDate checkInDate, LocalDate checkoutDate) {
        History history = new History(room, guest, checkInDate, checkoutDate);
        historyDao.save(history);
        return history;
    }

    public void deleteById(Integer id) {
        historyDao.deleteById(id);
    }

    @Override
    public void checkIn(Integer guestId, Integer roomId, LocalDate checkInDate, LocalDate checkoutDate) {
        Room room = roomDao.getById(roomId);
        Guest guest = guestDao.getById(guestId);
        if (room.getGuestsInRoom() < room.getCapacity()) {
            History history = addHistory(room, guest, checkInDate, checkoutDate);
            history.setId(IdGenerator.generateHistoryId());
            historyDao.update(history);
            guest.setHistories(historyDao.getAll());
            guest.setRoom(room);
            guest.setCheckIn(true);
            guest.setLastHistory(history);
            guestDao.update(guest);
            room.getGuests().add(guest);
            room.setStatus(RoomStatus.BUSY);
            room.incrementNumberOfGuests();
            room.setLastHistory(history);
            roomDao.update(room);
        } else System.out.println("Room busy");
    }

    @Override
    public void checkOut(Integer guestId) {
        Guest guest = guestDao.getById(guestId);
        Room room = roomDao.getById(guest.getRoom().getId());
        History history = guest.getLastHistory();
        room.getGuests().remove(guest);
        room.decrementNumberOfGuests();
        guest.setRoom(null);
        guest.setCheckIn(false);
        if (room.getGuests().isEmpty()) {
            room.setStatus(RoomStatus.FREE);
        }

        guest.getServices().clear();

        roomDao.update(room);
        guestDao.update(guest);
        historyDao.update(history);
    }

    @Override
    public Float getCostOfLiving(Integer guestId) {
        Guest guest = guestDao.getById(guestId);
        return guest.getLastHistory().getCost();
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
    public List<History> getAll() {
        return historyDao.getAll();
    }

    @Override
    public List<Service> getListOfGuestService(Integer guestId) {
        Guest guest = guestDao.getById(guestId);
        return guest.getServices();
    }
}
