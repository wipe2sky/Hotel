package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.DBConnector;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class GuestService implements IGuestService {
    private final Logger logger = LoggerFactory.getLogger(GuestService.class);
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;
    private final DBConnector connector;




    @InjectByType
    public GuestService(IGuestDao guestDao, IHistoryDao historyDao, DBConnector connector) {
        this.guestDao = guestDao;
        this.historyDao = historyDao;
        this.connector = connector;
    }

    @Override
    public Guest add(String lastName, String firstName) {
        Guest guest = new Guest(lastName, firstName);
        connector.startTransaction();
        guestDao.save(guest);
        connector.finishTransaction();
        return guest;
    }

    @Override
    public  Guest getById(Integer id) {
        try {
            return guestDao.getById(id);
        } catch (DaoException e) {
            logger.warn("Couldn't find entity by id: " + id, e);
            throw new ServiceException("Couldn't find entity by id: " + id, e);
        }
    }

    @Override
    public void deleteGuest(Integer id) {
        try {
            connector.startTransaction();
            guestDao.delete(getById(id));
        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Delete guest failed.", e);
            throw new ServiceException("Delete guest failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<Guest> getSortBy(SortStatus sortStatus) {
        return guestDao.getSortBy(sortStatus);

    }

    @Override
    public Integer getCountGuestInHotel() {
        return getAll().stream()
                .filter(Guest::isCheckIn)
                .collect(Collectors.toList()).size();
    }

    @Override
    public List<Guest> getAll() {
        return guestDao.getAll();
    }


    @Override
    public List<Guest> getAllGuestInHotel() {
        return getAll().stream()
                .filter(Guest::isCheckIn)
                .collect(Collectors.toList());
    }
}
