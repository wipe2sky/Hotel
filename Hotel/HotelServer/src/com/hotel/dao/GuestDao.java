package com.hotel.dao;

import com.hotel.api.dao.IGuestDao;
import com.hotel.model.Guest;



public class GuestDao extends AbstractDao<Guest> implements IGuestDao {
    private static GuestDao instance;

    private GuestDao() {
    }

    public static GuestDao getInstance() {
        if(instance == null) instance = new GuestDao();
        return instance;
    }

    @Override
    public Guest update(Guest entity) {
        Guest guest = getById(entity.getId());
        guest.setFirstName(entity.getFirstName());
        guest.setLastName(entity.getLastName());
        guest.setRoom(entity.getRoom());
        guest.setLastHistory(entity.getLastHistory());
        guest.setCheckIn(entity.isCheckIn());
        guest.setServices(entity.getServices());
        guest.setHistories(entity.getHistories());
        return guest;
    }


}
