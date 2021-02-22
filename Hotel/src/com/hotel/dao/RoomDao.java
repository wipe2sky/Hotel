package com.hotel.dao;

import com.hotel.api.dao.IRoomDao;
import com.hotel.model.Room;

import java.util.Objects;


public class RoomDao extends AbstractDao<Room> implements IRoomDao {

    private static RoomDao instance;

    private RoomDao() {
    }

    public static RoomDao getInstance() {
        if(instance == null) instance = new RoomDao();
        return instance;
    }

    @Override
    public Room update(Room entity) {
        Room room = getById(entity.getId());
        room.setNumber(entity.getNumber());
        room.setCapacity(entity.getCapacity());
        room.setStars(entity.getStars());
        room.setGuestsInRoom(entity.getGuestsInRoom());
        room.setPrice(entity.getPrice());
        room.setStatus(entity.getStatus());
        room.setIsCleaning(entity.getIsCleaning());
        room.setGuests(entity.getGuests());
        room.setHistories(entity.getHistories());
        return room;
    }
}
