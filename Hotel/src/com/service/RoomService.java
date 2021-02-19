package com.service;

import com.api.dao.IRoomDao;
import com.api.service.IRoomService;
import com.model.Room;
import com.model.RoomStatus;
import com.util.IdGenerator;
import com.util.comparators.ComparatorStatus;
import com.util.comparators.RoomCapacityComparator;
import com.util.comparators.RoomPriceComparator;
import com.util.comparators.RoomStarsComparator;

import java.time.LocalDate;
import java.util.*;

public class RoomService implements IRoomService {
    private final IRoomDao roomDao;
    private final Map<ComparatorStatus, Comparator<Room>> comparatorMap = new HashMap<>();

    private void initMap() {
        comparatorMap.put(ComparatorStatus.PRICE, new RoomPriceComparator());
        comparatorMap.put(ComparatorStatus.CAPACITY, new RoomCapacityComparator());
        comparatorMap.put(ComparatorStatus.STARS, new RoomStarsComparator());
    }


    public RoomService(IRoomDao roomDao) {
        this.roomDao = roomDao;
        initMap();
    }

    @Override
    public Room addRoom(Integer number, Integer capacity, Integer stars, Float price) {
        Room room = new Room(number, capacity, stars, price);
        room.setId(IdGenerator.generateRoomId());
        roomDao.save(room);
        return room;
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room = roomDao.getById(roomId);
        room.setIsCleaning(status);
        roomDao.update(room);
    }

    @Override
    public void setPrice(Integer roomId, Float price) {
        Room room = roomDao.getById(roomId);
        room.setPrice(price);
        roomDao.update(room);
    }

    @Override
    public List<Room> getSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        List<Room> rooms = roomDao.getAll();
        if (roomStatus.equals(RoomStatus.FREE)) {
            for (Room room :
                    roomDao.getAll()) {
                if (!room.getStatus().equals(RoomStatus.FREE)) rooms.remove(room);
            }
        }
        rooms.sort(comparatorMap.get(comparatorStatus));
        return rooms;
    }

    @Override
    public List<Room> getAvailableAfterDate(LocalDate date) {
        List<Room> rooms = new ArrayList<>();
        for (Room room :
                roomDao.getAll()) {
            if (room.getStatus().equals(RoomStatus.FREE) || room.getLastHistory().getCheckOutDate().minusDays(1).isBefore(date))
                rooms.add(room);
        }
        return rooms;
    }

    @Override
    public Integer getNumberOfFree() {
        Integer count = 0;
        for (Room room :
                roomDao.getAll()) {
            if (room.getStatus().equals(RoomStatus.FREE)) count++;
        }
        return count;
    }

    @Override
    public Room getInfo(Integer roomId) {
        return roomDao.getById(roomId);
    }

    @Override
    public void setRepairStatus(Integer roomId, boolean bol) {
        if(bol) {
            roomDao.getById(roomId).setStatus(RoomStatus.ON_REPAIR);
        } else roomDao.getById(roomId).setStatus(RoomStatus.FREE);

    }
}
