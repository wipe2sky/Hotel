package com.hotel.service;

import com.hotel.api.dao.IGuestDao;
import com.hotel.api.service.IGuestService;
import com.hotel.dao.GuestDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.Guest;
import com.hotel.util.generator.IdGenerator;
import com.hotel.util.comparators.ComparatorStatus;
import com.hotel.util.comparators.GuestDateComparator;
import com.hotel.util.comparators.GuestLastNameComparator;
import com.hotel.util.Logger;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class GuestService implements IGuestService{
    private static final Logger logger = new Logger(GuestService.class.getName());

    private static GuestService instance;
    private final IGuestDao guestDao;
    private final EnumMap<ComparatorStatus, Comparator<Guest>> comparatorMap = new EnumMap<>(ComparatorStatus.class);

    private void initMap() {
        comparatorMap.put(ComparatorStatus.DATE_CHECK_OUT, new GuestDateComparator());
        comparatorMap.put(ComparatorStatus.LAST_NAME, new GuestLastNameComparator());
    }

    private GuestService() {
        this.guestDao = GuestDao.getInstance();
    }

    public static GuestService getInstance() {
        if (instance == null) {
            instance = new GuestService();
            instance.initMap();
        }
        return instance;
    }

    @Override
    public Guest add(String firstName, String lastName) {
        Guest guest = new Guest(firstName, lastName);
        guest.setId(IdGenerator.getInstance().generateGuestId());
        guestDao.save(guest);
        return guest;
    }

    @Override
    public Guest getById(Integer id) {
        try {
            return guestDao.getById(id);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: " + id);
            throw new ServiceException("Couldn't find entity by id: " + id);
        }
    }

    @Override
    public void deleteGuest(Integer id) {
        try {
            guestDao.delete(getById(id));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Delete guest failed.");
            throw new ServiceException("Delete guest failed.");
        }
    }

    @Override
    public List<Guest> getShortBy(ComparatorStatus comparatorStatus) {
        return getAll().stream()
                .filter(Guest::isCheckIn)
                .sorted(comparatorMap.get(comparatorStatus))
                .collect(Collectors.toList());
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
