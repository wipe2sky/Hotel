package com.hotel.dao;

import com.hotel.api.dao.IGuestDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.Guest;
import com.hotel.util.Logger;
import com.hotel.util.SerializationHandler;


public class GuestDao extends AbstractDao<Guest> implements IGuestDao {
    private static GuestDao instance;

    private GuestDao() {
    }

    public static GuestDao getInstance() {
        if (instance == null) {
            instance = new GuestDao();
            try {
                instance.repository.addAll(SerializationHandler.deserialize(Guest.class));
            } catch (ServiceException e) {
                instance.logger.log(Logger.Level.WARNING, "Deserialization failed");
            }
        }
        return instance;
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
