package com.service;

import com.api.dao.IGuestDao;
import com.api.dao.IHistoryDao;
import com.api.dao.IRoomDao;
import com.api.service.IHistoryService;
import com.model.*;
import com.util.IdGenerator;
import com.util.comparators.HistoryDateOutComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryService implements IHistoryService {
    private final IGuestDao guestDao;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;

    public HistoryService(IGuestDao guestDao, IRoomDao roomDao, IHistoryDao historyDao) {
        this.guestDao = guestDao;
        this.roomDao = roomDao;
        this.historyDao = historyDao;
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
        List<History> histories = new ArrayList<>();
        for (History history :
                historyDao.getAll()) {
            if (history.getRoom().getId().equals(roomId)) histories.add(history);
        }
        histories.sort(new HistoryDateOutComparator());
        Collections.reverse(histories);

        List<History> las3History = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            las3History.add(histories.get(i));
        }
        return las3History;
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
