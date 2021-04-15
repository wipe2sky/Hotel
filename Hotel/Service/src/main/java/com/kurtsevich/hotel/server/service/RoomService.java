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
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    @Value("${roomService.allowRoomStatus}")
    private boolean allowRoomStatus;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;

    @Override
    public Room addRoom(Integer number, Integer capacity, Integer stars, Double price) {
        try {
            Room room = new Room(number, capacity, stars, price);
            roomDao.save(room);
            return room;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Add room failed.");
        }
    }

    @Override
    public void deleteRoom(Integer id) {
        try {
            roomDao.delete(roomDao.getById(id));

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete room failed.");
        }
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room;
        try {
            if (!allowRoomStatus) {
                throw new ServiceException("Changed status disable.");
            }
            room = roomDao.getById(roomId);

            if (status.equals(room.getIsCleaning())) {
                log.warn("Impossible change status {} on {}", room.getIsCleaning(), room.getIsCleaning());
                throw new ServiceException("Set cleaning status failed.");
            }
            room.setIsCleaning(status);
            roomDao.update(room);

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Set cleaning status failed.");
        }
    }

    @Override
    public void changePrice(Integer roomId, Double price) {
        try {
            Room room = roomDao.getById(roomId);
            room.setPrice(price);
            roomDao.update(room);

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Change room price failed.");
        }
    }

    @Override
    public List<Room> getSortBy(SortStatus sortStatus, RoomStatus roomStatus) {
        try {
            return roomDao.getSortBy(sortStatus, roomStatus);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sorting room failed.");
        }
    }

    @Override
    @Transactional
    public List<Room> getAvailableAfterDate(LocalDateTime date) {
        try {
            return roomDao.getAvailableAfterDate(date);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    public Integer getNumberOfFree() {
        try {
            return roomDao.getNumberOfFree();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get count of free rooms failed.");
        }
    }


    @Override
    public List<History> getRoomHistory(Integer roomId) {
        try {
            return historyDao.getRoomHistories(roomDao.getById(roomId));

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get room History failed.");
        }
    }

    @Override
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
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Set repair status failed.");
        }
    }

    @Override
    public List<Room> getAll() {
        try {
            return roomDao.getAll();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    public Room getById(Integer roomId) {
        try {
            return roomDao.getById(roomId);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get room by id failed.");
        }
    }
}
