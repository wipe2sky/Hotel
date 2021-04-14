package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.util.SortStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class GuestService implements IGuestService {
    private final IGuestDao guestDao;

    @Override
    public Guest add(String lastName, String firstName) {
        try {
            Guest guest = new Guest(lastName, firstName);
            guestDao.save(guest);
            return guest;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Couldn't add Guest");
        }
    }


    @Override
    public  Guest getById(Integer id) {
        try {
            return guestDao.getById(id);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage());
            throw new ServiceException("Couldn't find entity by id: " + id);
        }
    }

    @Override
    public void deleteGuest(Integer id) {
        try {
            guestDao.delete(guestDao.getById(id));
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete guest failed.");
        }
    }

    @Override
    public List<Guest> getSortBy(SortStatus sortStatus) {
        try {
            return guestDao.getSortBy(sortStatus);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sort guest failed.");
        }

    }

    @Override
    public Long getCountGuestInHotel() {
        try {
            return guestDao.getCountGuestInHotel();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get count of guest failed.");
        }
    }

    @Override
    public List<Guest> getAll() {
        try {
           return guestDao.getAll();

        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get guests failed.");
        }
    }


    @Override
    public List<Guest> getAllGuestInHotel() {
        try {
            return  guestDao.getAllGuestInHotel();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get all guests in hotel failed.");
        }
    }
}
