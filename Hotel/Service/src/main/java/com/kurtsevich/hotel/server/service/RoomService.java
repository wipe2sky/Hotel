package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class RoomService implements IRoomService {
    private final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @ConfigProperty
    private boolean allowRoomStatus;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;
    private final HibernateConnector connector;


    @InjectByType
    public RoomService(IRoomDao roomDao, IHistoryDao historyDao, HibernateConnector connector) {
        this.roomDao = roomDao;
        this.historyDao = historyDao;
        this.connector = connector;
    }


    @Override
    public Room addRoom(Integer number, Integer capacity, Integer stars, Double price) {
        try {
            Room room = new Room(number, capacity, stars, price);
            connector.startTransaction();
            roomDao.save(room);
            return room;
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Add room failed.", e);
            throw new ServiceException("Add room failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void deleteRoom(Integer id) {
        try {
            connector.startTransaction();
            roomDao.delete(roomDao.getById(id));

        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Delete room failed.", e);
            throw new ServiceException("Delete room failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room;
        try {
            connector.startTransaction();

            if (!allowRoomStatus) {
                throw new ServiceException("Changed status disable.");
            }
            room = roomDao.getById(roomId);

            if (status.equals(room.getIsCleaning())) {
                logger.warn("Set cleaning status failed");
                throw new ServiceException("Set cleaning status failed.");
            }
            room.setIsCleaning(status);
            roomDao.update(room);

        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Set cleaning status failed.", e);
            throw new ServiceException("Set cleaning status failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void changePrice(Integer roomId, Double price) {
        try {
            connector.startTransaction();

            Room room = roomDao.getById(roomId);
            room.setPrice(price);
            roomDao.update(room);

        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Change room price failed.", e);
            throw new ServiceException("Change room price failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<Room> getSortBy(SortStatus sortStatus, RoomStatus roomStatus) {
        try {
            connector.startTransaction();
            return roomDao.getSortBy(sortStatus, roomStatus);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Sorting room failed.", e);
            throw new ServiceException("Sorting room failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<Room> getAvailableAfterDate(LocalDateTime date) {
        try {
            connector.startTransaction();
            return historyDao.getAvailableAfterDate(date);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get rooms failed.", e);
            throw new ServiceException("Get rooms failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public Integer getNumberOfFree() {
        try {
            connector.startTransaction();
            return roomDao.getNumberOfFree();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get count of free rooms failed.", e);
            throw new ServiceException("Get count of free rooms failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }


    @Override
    public List<History> getRoomHistory(Integer roomId) {
        try {
            connector.startTransaction();
            return roomDao.getHistory(roomDao.getById(roomId));

        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get room history failed.", e);
            throw new ServiceException("Get room History failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void setRepairStatus(Integer roomId, boolean bol) {
        try {
            connector.startTransaction();
            Room room = roomDao.getById(roomId);

            if (!allowRoomStatus) {
                throw new ServiceException("Changed status disable.");
            }

            if (bol) {
                room.setStatus(RoomStatus.REPAIR);
            } else {
                room.setStatus(RoomStatus.FREE);
            }
            roomDao.update(room);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Set repair status failed.", e);
            throw new ServiceException("Set repair status failed.", e);
        } finally {
            connector.finishTransaction();

        }
    }

    @Override
    public List<Room> getAll() {
        try {
            connector.startTransaction();
            return roomDao.getAll();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get rooms failed.", e);
            throw new ServiceException("Get rooms failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public Room getById(Integer roomId) {
        try {
            connector.startTransaction();
            return roomDao.getById(roomId);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get room by id failed.", e);
            throw new ServiceException("Get room by id failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }
}
