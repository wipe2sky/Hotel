package com.kurtsevich.hotel.server.dao;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.SerializationHandler;

@Singleton
public class GuestDao extends AbstractDao<Guest> implements IGuestDao {

    @InjectByType
    public GuestDao(SerializationHandler serializationHandler) {
        try {
            repository.addAll(serializationHandler.deserialize(Guest.class));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Deserialization failed");
        }
    }

    @Override
    public Guest update(Guest entity) {
        try {
            Guest guest = getById(entity.getId());

            guest.setFirstName(entity.getFirstName());
            guest.setLastName(entity.getLastName());
            guest.setRoom(entity.getRoom());
            guest.setLastHistory(entity.getLastHistory());
            guest.setCheckIn(entity.isCheckIn());
            guest.setServices(entity.getServices());
            guest.setHistories(entity.getHistories());
            return guest;
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Guest update failed");
            throw new DaoException("Guest update failed", e);
        }
    }


}
