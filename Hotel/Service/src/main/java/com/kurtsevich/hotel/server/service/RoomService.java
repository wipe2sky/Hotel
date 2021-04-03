package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.ConfigProperty;
import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.model.AEntity;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.DBConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final DBConnector connector;


//    private final EnumMap<ComparatorStatus, Comparator<Room>> comparatorMap = new EnumMap<>(ComparatorStatus.class);

    @InjectByType
    public RoomService(IRoomDao roomDao, IHistoryDao historyDao, DBConnector connector) {
        this.roomDao = roomDao;
        this.historyDao = historyDao;
        this.connector = connector;
//        fillComparatorMap();

    }

//    private void fillComparatorMap() {
//        comparatorMap.put(ComparatorStatus.PRICE, new RoomPriceComparator());
//        comparatorMap.put(ComparatorStatus.CAPACITY, new RoomCapacityComparator());
//        comparatorMap.put(ComparatorStatus.STARS, new RoomStarsComparator());
//    }


    @Override
    public Room addRoom(Integer number, Integer capacity, Integer stars, Double price) {
        Room room = new Room(number, capacity, stars, price);
        connector.startTransaction();
        roomDao.save(room);
        connector.finishTransaction();
        return room;
    }

    @Override
    public void deleteRoom(Integer id) {
        try {
            connector.startTransaction();
            roomDao.delete(roomDao.getById(id));
            connector.finishTransaction();

        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Delete room failed.", e);
            throw new ServiceException("Delete room failed.", e);
        }
    }

    @Override
    public void setCleaningStatus(Integer roomId, Boolean status) {
        Room room;
        try {
            connector.startTransaction();
            room = roomDao.getById(roomId);
        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Set cleaning status failed.", e);
            throw new ServiceException("Set cleaning status failed.", e);
        }
        if (!allowRoomStatus) {
            connector.rollback();
            throw new ServiceException("Changed status disable.");
        }
        if (status.equals(room.getIsCleaning())) {
            connector.rollback();
            logger.warn("Set cleaning status failed");
            throw new ServiceException("Set cleaning status failed.");
        }

        room.setIsCleaning(status);
        roomDao.update(room);

        connector.finishTransaction();
    }

    @Override
    public void changePrice(Integer roomId, Double price) {
        try {
            connector.startTransaction();

            Room room = roomDao.getById(roomId);
            room.setPrice(price);
            roomDao.update(room);

            connector.finishTransaction();
        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Change room price failed.", e);
            throw new ServiceException("Change room price failed.", e);
        }
    }

    @Override
    public List<Room> getSortBy(SortStatus sortStatus, RoomStatus roomStatus) {
        return roomDao.getSortBy(sortStatus, roomStatus);
    }

    @Override
    public List<Room> getAvailableAfterDate(LocalDateTime date) {
        List<Room> rooms = new ArrayList<>();
        connector.startTransaction();

        rooms.addAll(getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.FREE))
                .collect(Collectors.toList()));
        rooms.addAll(getAll().stream()
                .filter(room -> room.getStatus().equals(RoomStatus.BUSY))
                .filter(room -> historyDao.getByRoom(room).get(0).getCheckOutDate().minusDays(1).isBefore(date))
                .collect(Collectors.toList()));

        connector.finishTransaction();

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
            connector.startTransaction();

            List<History> histories = historyDao.getByRoom(roomDao.getById(roomId)).stream()
                    .sorted(Comparator.comparing(AEntity::getId).reversed())
                    .limit(countOfHistories)
                    .collect(Collectors.toList());

            connector.finishTransaction();

            return histories;
        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Get room history failed.",e);
            throw new ServiceException("Get room History failed.",e);
        }
    }

    @Override
    public void setRepairStatus(Integer roomId, boolean bol) {
        Room room;
        try {
            connector.startTransaction();
            room = roomDao.getById(roomId);
        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Set repair status failed.", e);
            throw new ServiceException("Set repair status failed.",e);
        }
        if (!allowRoomStatus) {
            connector.rollback();
            throw new ServiceException("Changed status disable.");
        }

        if (bol) {
            room.setStatus(RoomStatus.REPAIR);
        } else room.setStatus(RoomStatus.FREE);
        roomDao.update(room);

        connector.finishTransaction();
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
