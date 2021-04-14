package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RoomService implements IRoomService {
    private final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Value("${roomService.allowRoomStatus}")
    private boolean allowRoomStatus;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;


    @Autowired
    public RoomService(IRoomDao roomDao, IHistoryDao historyDao) {
        this.roomDao = roomDao;
        this.historyDao = historyDao;
    }


    @Override
    @Transactional
    public Room addRoom(Integer number, Integer capacity, Integer stars, Double price) {
        try {
            Room room = new Room(number, capacity, stars, price);
            roomDao.save(room);
            return room;
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Add room failed.");
        }
    }

    @Override
    @Transactional
    public void deleteRoom(Integer id) {
        try {
            roomDao.delete(roomDao.getById(id));

        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete room failed.");
        }
    }

    @Override
    @Transactional
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room;
        try {
            if (!allowRoomStatus) {
                throw new ServiceException("Changed status disable.");
            }
            room = roomDao.getById(roomId);

            if (status.equals(room.getIsCleaning())) {
                logger.warn("Impossible change status {} on {}", room.getIsCleaning(), room.getIsCleaning());
                throw new ServiceException("Set cleaning status failed.");
            }
            room.setIsCleaning(status);
            roomDao.update(room);

        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Set cleaning status failed.");
        }
    }

    @Override
    @Transactional
    public void changePrice(Integer roomId, Double price) {
        try {
            Room room = roomDao.getById(roomId);
            room.setPrice(price);
            roomDao.update(room);

        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Change room price failed.");
        }
    }

    @Override
    @Transactional
    public List<Room> getSortBy(SortStatus sortStatus, RoomStatus roomStatus) {
        try {
            return roomDao.getSortBy(sortStatus, roomStatus);
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sorting room failed.");
        }
    }

    @Override
    @Transactional
    public List<Room> getAvailableAfterDate(LocalDateTime date) {
        try {
            return roomDao.getAvailableAfterDate(date);
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    @Transactional
    public Integer getNumberOfFree() {
        try {
            return roomDao.getNumberOfFree();
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get count of free rooms failed.");
        }
    }


    @Override
    @Transactional
    public List<History> getRoomHistory(Integer roomId) {
        try {
            return historyDao.getRoomHistories(roomDao.getById(roomId));

        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get room History failed.");
        }
    }

    @Override
    @Transactional
    public void setRepairStatus(Integer roomId, boolean bol) {
        try {
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
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Set repair status failed.");
        }
    }

    @Override
    @Transactional
    public List<Room> getAll() {
        try {
            return roomDao.getAll();
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    @Transactional
    public Room getById(Integer roomId) {
        try {
            return roomDao.getById(roomId);
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get room by id failed.");
        }
    }
}
