package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IRoomDao;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.SerializationHandler;

@Singleton
public class RoomDao extends AbstractDao<Room> implements IRoomDao {

    @InjectByType
    public RoomDao(SerializationHandler serializationHandler) {
        try {
            repository.addAll(serializationHandler.deserialize(Room.class));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed");
        }
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
