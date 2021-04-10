package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IHistoryService;
import com.kurtsevich.hotel.server.model.*;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Singleton
public class HistoryService implements IHistoryService {
    private final Logger logger = LoggerFactory.getLogger(HistoryService.class);
    private final IGuestDao guestDao;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;
    private final HibernateConnector connector;


    @InjectByType
    public HistoryService(IGuestDao guestDao, IRoomDao roomDao, IHistoryDao historyDao, HibernateConnector connector) {
        this.guestDao = guestDao;
        this.roomDao = roomDao;
        this.historyDao = historyDao;
        this.connector = connector;
    }


    @Override
    public History addHistory(Room room, Guest guest, Integer daysStay) {
        History history = new History(LocalDateTime.now(), LocalDateTime.now().plusDays(daysStay), room.getPrice() * daysStay, room, guest);
//        connector.startTransaction();
        historyDao.save(history);
//        connector.finishTransaction();
        return history;
    }


    @Override
    public void checkIn(Integer guestId, Integer roomId, Integer daysStay) {
        try {
            connector.startTransaction();
            Room room = roomDao.getById(roomId);
            Guest guest = guestDao.getById(guestId);
            if (room.getGuestsInRoom() < room.getCapacity()) {
                logger.info("Check-in of the guest № {} to the room № {} for {} days", guestId, roomId, daysStay);
                guest.setCheckIn(true);
                guestDao.update(guest);
                room.setStatus(RoomStatus.BUSY);
                room.setGuestsInRoom(room.getGuestsInRoom() + 1);
                roomDao.update(room);
                addHistory(room, guest, daysStay);
            } else {
                logger.info("Room {} busy.", roomId);
            }

        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Chek-in failed.", e);
            throw new ServiceException("Chek-in failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void checkOut(Integer guestId) {
        try {
            connector.startTransaction();
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getByGuest(guest).get(0);
            Room room = history.getRoom();

            logger.info("Check-out of the guest № {} to the room № {}", guestId, room.getId());
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
            connector.rollbackTransaction();
            logger.warn("Chek-out failed.", e);
            throw new ServiceException("Chek-out failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public Double getCostOfLiving(Integer guestId) {
        try {
            connector.startTransaction();
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getByGuest(guest).get(0);
            return history.getCostOfLiving();
        } catch (DaoException e) {
            logger.warn("Get cost of living failed.", e);
            throw new ServiceException("Get cost of living failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<Guest> getLast3GuestInRoom(Integer roomId) {
        try {
            connector.startTransaction();
            return historyDao.getLast3GuestInRoom(roomDao.getById(roomId));
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get guests in Room failed.", e);
            throw new ServiceException("Get guests in Room failed.", e);
        } finally {
            connector.finishTransaction();
        }

    }

    @Override
    public List<History> getGuestHistory(Integer id) {
        try {
            connector.startTransaction();
            return guestDao.getHistory(guestDao.getById(id));
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get guests history failed.", e);
            throw new ServiceException("Get guests history failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<History> getAll() {
        try {
            connector.startTransaction();
            return historyDao.getAll();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get guests failed.", e);
            throw new ServiceException("Get guests failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }


    @Override
    public List<Service> getListOfGuestService(Integer guestId) {
        try {
            connector.startTransaction();

            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getByGuest(guest).get(0);

            return history.getServices();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get list of guest service failed.", e);
            throw new ServiceException("Get list of guest service failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }
}
