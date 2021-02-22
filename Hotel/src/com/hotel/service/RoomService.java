package com.hotel.service;

import com.hotel.api.dao.IRoomDao;
import com.hotel.api.service.IRoomService;
import com.hotel.dao.RoomDao;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.util.IdGenerator;
import com.hotel.util.comparators.ComparatorStatus;
import com.hotel.util.comparators.RoomCapacityComparator;
import com.hotel.util.comparators.RoomPriceComparator;
import com.hotel.util.comparators.RoomStarsComparator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomService implements IRoomService {
    private static RoomService instance;
    private final IRoomDao roomDao;
    private final Map<ComparatorStatus, Comparator<Room>> comparatorMap = new HashMap<>();

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
        roomDao.delete(roomDao.getById(id));
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room = roomDao.getById(roomId);
        room.setIsCleaning(status);
        roomDao.update(room);
    }

    @Override
    public void changePrice(Integer roomId, Float price) {
        Room room = roomDao.getById(roomId);
        room.setPrice(price);
        roomDao.update(room);
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
        long count = roomDao.getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .count();
        return (int) count;
    }

    @Override
    public Room getInfo(Integer roomId) {
        return roomDao.getById(roomId);
    }

    @Override
    public void setRepairStatus(Integer roomId, boolean bol) {
        Room room = roomDao.getById(roomId);
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
