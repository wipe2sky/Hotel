package com.hotel.service;

import com.hotel.api.dao.IRoomDao;
import com.hotel.api.service.IRoomService;
import com.hotel.dao.RoomDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.util.IdGenerator;
import com.hotel.util.comparators.ComparatorStatus;
import com.hotel.util.comparators.RoomCapacityComparator;
import com.hotel.util.comparators.RoomPriceComparator;
import com.hotel.util.comparators.RoomStarsComparator;
import com.hotel.util.logger.Logger;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RoomService implements IRoomService {
    private static final Logger logger = new Logger(RoomService.class.getName());

    private static RoomService instance;
    private final IRoomDao roomDao;
    private final EnumMap<ComparatorStatus, Comparator<Room>> comparatorMap = new EnumMap<>(ComparatorStatus.class);

    private void initMap() {
        comparatorMap.put(ComparatorStatus.PRICE, new RoomPriceComparator());
        comparatorMap.put(ComparatorStatus.CAPACITY, new RoomCapacityComparator());
        comparatorMap.put(ComparatorStatus.STARS, new RoomStarsComparator());
    }


    private RoomService() {
        this.roomDao = RoomDao.getInstance();
        initMap();
    }

    public static RoomService getInstance() {
        if (instance == null) instance = new RoomService();
        return instance;
    }

    @Override
    public Room addRoom(Integer number, Integer capacity, Integer stars, Float price) {
        Room room = new Room(number, capacity, stars, price);
        room.setId(IdGenerator.generateRoomId());
        roomDao.save(room);
        return room;
    }

    @Override
    public void deleteRoom(Integer id) {
        try {
            roomDao.delete(roomDao.getById(id));
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Delete room failed.");
            throw new ServiceException("Delete room failed.");
        }
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        try {
            Room room = roomDao.getById(roomId);
            room.setIsCleaning(status);
            roomDao.update(room);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Set cleaning status failed.");
            throw new ServiceException("Set cleaning status failed.");
        }
    }

    @Override
    public void changePrice(Integer roomId, Float price) {
        try {
            Room room = roomDao.getById(roomId);
            room.setPrice(price);
            roomDao.update(room);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Change room price failed.");
            throw new ServiceException("Change room price failed.");
        }
    }

    @Override
    public List<Room> getSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        return roomDao.getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .sorted(comparatorMap.get(comparatorStatus))
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> getAvailableAfterDate(LocalDate date) {
        return roomDao.getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE)
                        || room.getLastHistory().getCheckOutDate().minusDays(1).isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getNumberOfFree() {
        return (int) roomDao.getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .count();
    }

    @Override
    public Room getInfo(Integer roomId) {
        try {
            return roomDao.getById(roomId);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get room info failed.");
            throw new ServiceException("Get room imfo failed.");
        }
    }

    @Override
    public void setRepairStatus(Integer roomId, boolean bol) {
        try {
            Room room = roomDao.getById(roomId);
            if (bol) {
                room.setStatus(RoomStatus.ON_REPAIR);
            } else room.setStatus(RoomStatus.FREE);
            roomDao.update(room);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Set repair status failed.");
            throw new ServiceException("Set repair status failed.");
        }
    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }
}
