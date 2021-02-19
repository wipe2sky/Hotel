package com.dao;

import com.api.dao.IRoomDao;
import com.model.Room;


public class RoomDao extends AbstractDao<Room> implements IRoomDao {



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
