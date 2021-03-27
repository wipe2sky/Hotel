package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.AEntity;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.IdGenerator;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;
import com.kurtsevich.hotel.server.util.comparators.RoomCapacityComparator;
import com.kurtsevich.hotel.server.util.comparators.RoomPriceComparator;
import com.kurtsevich.hotel.server.util.comparators.RoomStarsComparator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class RoomService implements IRoomService {
    private static final Logger logger = new Logger(RoomService.class.getName());
    private final IdGenerator idGenerator;
    @ConfigProperty
    private Integer countOfHistories;
    @ConfigProperty
    private boolean allowRoomStatus;
    private final IRoomDao roomDao;
    private final EnumMap<ComparatorStatus, Comparator<Room>> comparatorMap = new EnumMap<>(ComparatorStatus.class);

    @InjectByType
    public RoomService(IdGenerator idGenerator, IRoomDao roomDao) {
        this.idGenerator = idGenerator;
        this.roomDao = roomDao;
        comparatorMap.put(ComparatorStatus.PRICE, new RoomPriceComparator());
        comparatorMap.put(ComparatorStatus.CAPACITY, new RoomCapacityComparator());
        comparatorMap.put(ComparatorStatus.STARS, new RoomStarsComparator());
    }


    @Override
    public Room addRoom(Integer number, Integer capacity, Integer stars, Float price) {
        Room room = new Room(number, capacity, stars, price);
        room.setId(idGenerator.generateRoomId());
        roomDao.save(room);
        return room;
    }

    @Override
    public void deleteRoom(Integer id) {
        try {
            roomDao.delete(getInfo(id));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Delete room failed.", e);
            throw new ServiceException("Delete room failed.", e);
        }
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room;
        try {
            room = getInfo(roomId);
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Set cleaning status failed.", e);
            throw new ServiceException("Set cleaning status failed.", e);
        }
        if (!allowRoomStatus) throw new ServiceException("Changed status disable.");
        if (status.equals(room.getIsCleaning())) {
            logger.log(Logger.Level.WARNING, "Change of status to the same.");
            throw new ServiceException("Set cleaning status failed.");
        }

        room.setIsCleaning(status);
        roomDao.update(room);

    }

    @Override
    public void changePrice(Integer roomId, Float price) {
        try {
            Room room = getInfo(roomId);
            room.setPrice(price);
            roomDao.update(room);
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Change room price failed.", e);
            throw new ServiceException("Change room price failed.", e);
        }
    }

    @Override
    public List<Room> getSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        return getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .sorted(comparatorMap.get(comparatorStatus))
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> getAvailableAfterDate(LocalDate date) {
        return getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE)
                        || room.getLastHistory().getCheckOutDate().minusDays(1).isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getNumberOfFree() {
        return (int) getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .count();
    }

    @Override
    public Room getInfo(Integer roomId) {
        try {
            return roomDao.getById(roomId);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get room info failed.", e);
            throw new ServiceException("Get room info failed.", e);
        }
    }

    @Override
    public List<History> getRoomHistory(Integer roomId) {
        try {
            return getInfo(roomId).getHistories().stream()
                    .sorted(Comparator.comparing(AEntity::getId).reversed())
                    .limit(countOfHistories)
                    .collect(Collectors.toList());
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Get room history failed.",e);
            throw new ServiceException("Get room History failed.",e);
        }
    }

    @Override
    public void setRepairStatus(Integer roomId, boolean bol) {
        Room room;
        try {
            room = getInfo(roomId);
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Set repair status failed.", e);
            throw new ServiceException("Set repair status failed.",e);
        }
        if (!allowRoomStatus) throw new ServiceException("Changed status disable.");

        if (bol) {
            room.setStatus(RoomStatus.ON_REPAIR);
        } else room.setStatus(RoomStatus.FREE);
        roomDao.update(room);

    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }
}
