package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.AEntity;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;
import com.kurtsevich.hotel.server.util.comparators.RoomCapacityComparator;
import com.kurtsevich.hotel.server.util.comparators.RoomPriceComparator;
import com.kurtsevich.hotel.server.util.comparators.RoomStarsComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class RoomService implements IRoomService {
    private final Logger logger = LoggerFactory.getLogger(RoomService.class);
    @ConfigProperty
    private Integer countOfHistories;
    @ConfigProperty
    private boolean allowRoomStatus;
    private final IRoomDao roomDao;
    private final IHistoryDao historyDao;

    private final EnumMap<ComparatorStatus, Comparator<Room>> comparatorMap = new EnumMap<>(ComparatorStatus.class);

    @InjectByType
    public RoomService(IRoomDao roomDao, IHistoryDao historyDao) {
        this.roomDao = roomDao;
        this.historyDao = historyDao;
        comparatorMap.put(ComparatorStatus.PRICE, new RoomPriceComparator());
        comparatorMap.put(ComparatorStatus.CAPACITY, new RoomCapacityComparator());
        comparatorMap.put(ComparatorStatus.STARS, new RoomStarsComparator());
    }


    @Override
    public Room addRoom(Integer number, Integer capacity, Integer stars, Double price) {
        Room room = new Room(number, capacity, stars, price);
        roomDao.save(room);
        return room;
    }

    @Override
    public void deleteRoom(Integer id) {
        try {
            roomDao.delete(roomDao.getById(id));
        } catch (ServiceException e) {
            logger.warn("Delete room failed.", e);
            throw new ServiceException("Delete room failed.", e);
        }
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room;
        try {
            room = roomDao.getById(roomId);
        } catch (ServiceException e) {
            logger.warn("Set cleaning status failed.", e);
            throw new ServiceException("Set cleaning status failed.", e);
        }
        if (!allowRoomStatus) throw new ServiceException("Changed status disable.");
        if (status.equals(room.getIsCleaning())) {
            logger.warn("Set cleaning status failed");
            throw new ServiceException("Set cleaning status failed.");
        }

        room.setIsCleaning(status);
        roomDao.update(room);

    }

    @Override
    public void changePrice(Integer roomId, Double price) {
        try {
            Room room = roomDao.getById(roomId);
            room.setPrice(price);
            roomDao.update(room);
        } catch (ServiceException e) {
            logger.warn("Change room price failed.", e);
            throw new ServiceException("Change room price failed.", e);
        }
    }

    @Override
    public List<Room> getSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        return roomStatus == null
                ? getAll().stream()
                .sorted(comparatorMap.get(comparatorStatus))
                .collect(Collectors.toList())
                : getAll().stream()
                .filter(room -> room.getStatus().equals(roomStatus))
                .sorted(comparatorMap.get(comparatorStatus))
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> getAvailableAfterDate(LocalDateTime date) {
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .collect(Collectors.toList()));
        rooms.addAll(getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.BUSY))
                .filter(room -> historyDao.getByRoom(room).get(0).getCheckOutDate().minusDays(1).isBefore(date))
                .collect(Collectors.toList()));
        return rooms;
    }

    @Override
    public Integer getNumberOfFree() {
        return (int) getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .count();
    }


    @Override
    public List<History> getRoomHistory(Integer roomId) {
        try {
            return historyDao.getByRoom(roomDao.getById(roomId)).stream()
                    .sorted(Comparator.comparing(AEntity::getId).reversed())
                    .limit(countOfHistories)
                    .collect(Collectors.toList());
        } catch (ServiceException e) {
            logger.warn("Get room history failed.",e);
            throw new ServiceException("Get room History failed.",e);
        }
    }

    @Override
    public void setRepairStatus(Integer roomId, boolean bol) {
        Room room;
        try {
            room = roomDao.getById(roomId);
        } catch (ServiceException e) {
            logger.warn("Set repair status failed.", e);
            throw new ServiceException("Set repair status failed.",e);
        }
        if (!allowRoomStatus) throw new ServiceException("Changed status disable.");

        if (bol) {
            room.setStatus(RoomStatus.REPAIR);
        } else room.setStatus(RoomStatus.FREE);
        roomDao.update(room);

    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }

    @Override
    public Room getInfo(Integer roomId) {
        return roomDao.getById(roomId);
    }
}
