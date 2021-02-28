package com.hotel.dao;

import com.hotel.api.dao.IRoomDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.Room;
import com.hotel.util.Logger;
import com.hotel.util.SerializationHandler;

public class RoomDao extends AbstractDao<Room> implements IRoomDao {

    private static RoomDao instance;

    private RoomDao() {
    }

    public static RoomDao getInstance() {
        if (instance == null) {
            instance = new RoomDao();
            try {
                instance.repository.addAll(SerializationHandler.deserialize(Room.class));
            } catch (ServiceException e) {
                instance.logger.log(Logger.Level.WARNING, "Deserialization failed");
            }
        }
        return instance;
    }

    @Override
    public Room update(Room entity) {
        try {
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
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Room update failed");
            throw new DaoException("Room update failed", e);
        }
    }
}
