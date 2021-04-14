package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GuestService implements IGuestService {
    private final Logger logger = LoggerFactory.getLogger(GuestService.class);
    private final IGuestDao guestDao;


    @Autowired
    public GuestService(IGuestDao guestDao) {
        this.guestDao = guestDao;
    }

    @Override
    @Transactional
    public Guest add(String lastName, String firstName) {
        try {
            Guest guest = new Guest(lastName, firstName);
            guestDao.save(guest);
            return guest;
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Couldn't add Guest");
        }
    }


    @Override
    @Transactional
    public  Guest getById(Integer id) {
        try {
            return guestDao.getById(id);
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage());
            throw new ServiceException("Couldn't find entity by id: " + id);
        }
    }

    @Override
    @Transactional
    public void deleteGuest(Integer id) {
        try {
            guestDao.delete(guestDao.getById(id));
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete guest failed.");
        }
    }

    @Override
    @Transactional
    public List<Guest> getSortBy(SortStatus sortStatus) {
        try {
            return guestDao.getSortBy(sortStatus);
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sort guest failed.");
        }

    }

    @Override
    @Transactional
    public Long getCountGuestInHotel() {
        try {
            return guestDao.getCountGuestInHotel();
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get count of guest failed.");
        }
    }

    @Override
    @Transactional
    public List<Guest> getAll() {
        try {
           return guestDao.getAll();

        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get guests failed.");
        }
    }


    @Override
    @Transactional
    public List<Guest> getAllGuestInHotel() {
        try {
            return  guestDao.getAllGuestInHotel();
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get all guests in hotel failed.");
        }
    }
}
