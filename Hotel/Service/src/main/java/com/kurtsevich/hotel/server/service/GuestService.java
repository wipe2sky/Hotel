package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuestService implements IGuestService {
    private final Logger logger = LoggerFactory.getLogger(GuestService.class);
    private final IGuestDao guestDao;
    private final HibernateConnector connector;


    @Autowired
    public GuestService(IGuestDao guestDao, HibernateConnector connector) {
        this.guestDao = guestDao;
        this.connector = connector;
    }

    @Override
    public Guest add(String lastName, String firstName) {
        try {
            Guest guest = new Guest(lastName, firstName);
            connector.startTransaction();
            guestDao.save(guest);
            return guest;
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Couldn't add Guest");
        }finally {
            connector.finishTransaction();
        }
    }

    @Override
    public  Guest getById(Integer id) {
        try {
            connector.startTransaction();
            return guestDao.getById(id);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn(e.getLocalizedMessage());
            throw new ServiceException("Couldn't find entity by id: " + id);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void deleteGuest(Integer id) {
        try {
            connector.startTransaction();
            guestDao.delete(guestDao.getById(id));
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete guest failed.");
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<Guest> getSortBy(SortStatus sortStatus) {
        try {
            connector.startTransaction();
            return guestDao.getSortBy(sortStatus);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sort guest failed.");
        } finally {
            connector.finishTransaction();
        }

    }

    @Override
    public Long getCountGuestInHotel() {
        try {
            connector.startTransaction();
            return guestDao.getCountGuestInHotel();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get count of guest failed.");
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<Guest> getAll() {
        try {
            connector.startTransaction();
           return guestDao.getAll();

        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get guests failed.");
        } finally {
            connector.finishTransaction();
        }
    }


    @Override
    public List<Guest> getAllGuestInHotel() {
        try {
            connector.startTransaction();
            return  guestDao.getAllGuestInHotel();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get all guests in hotel failed.");
        } finally {
            connector.finishTransaction();
        }
    }
}
