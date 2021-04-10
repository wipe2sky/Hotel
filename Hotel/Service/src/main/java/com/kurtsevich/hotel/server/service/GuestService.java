package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.HibernateConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class GuestService implements IGuestService {
    private final Logger logger = LoggerFactory.getLogger(GuestService.class);
    private final IGuestDao guestDao;
    private final HibernateConnector connector;


    @InjectByType
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
            logger.warn("Couldn't add Guest", e);
            throw new ServiceException("Couldn't add Guest", e);
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
            logger.warn("Couldn't find entity by id: " + id, e);
            throw new ServiceException("Couldn't find entity by id: " + id, e);
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
            logger.warn("Delete guest failed.", e);
            throw new ServiceException("Delete guest failed.", e);
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
            logger.warn("Sort guest failed.", e);
            throw new ServiceException("Sort guest failed.", e);
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
            logger.warn("Get count of guest failed.", e);
            throw new ServiceException("Get count of guest failed.", e);
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
            logger.warn("Get guests failed.", e);
            throw new ServiceException("Get guests failed.", e);
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
            logger.warn("Get all guests in hotel failed.", e);
            throw new ServiceException("Get all guests in hotel failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }
}
