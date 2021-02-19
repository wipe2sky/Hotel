package com.dao;

import com.api.dao.IGuestDao;
import com.model.Guest;


public class GuestDao extends AbstractDao<Guest> implements IGuestDao {

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
