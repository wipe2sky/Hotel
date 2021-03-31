package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;
import com.kurtsevich.hotel.server.util.comparators.GuestDateComparator;
import com.kurtsevich.hotel.server.util.comparators.GuestLastNameComparator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class GuestService implements IGuestService {
    private static final Logger logger = new Logger(GuestService.class.getName());
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;


    private final EnumMap<ComparatorStatus, Comparator<History>> comparatorMap = new EnumMap<>(ComparatorStatus.class);


    @InjectByType
    public GuestService(IGuestDao guestDao, IHistoryDao historyDao) {
        this.guestDao = guestDao;
        this.historyDao = historyDao;
        comparatorMap.put(ComparatorStatus.DATE_CHECK_OUT, new GuestDateComparator());
        comparatorMap.put(ComparatorStatus.LAST_NAME, new GuestLastNameComparator());
    }

    @Override
    public Guest add(String lastName, String firstName) {
        Guest guest = new Guest(lastName, firstName);
        guestDao.save(guest);
        return guest;
    }

    @Override
    public  Guest getById(Integer id) {
        try {
            return guestDao.getById(id);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Couldn't find entity by id: " + id, e);
            throw new ServiceException("Couldn't find entity by id: " + id, e);
        }
    }

    @Override
    public void deleteGuest(Integer id) {
        try {
            guestDao.delete(getById(id));
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Delete guest failed.", e);
            throw new ServiceException("Delete guest failed.", e);
        }
    }

    @Override
    public List<Guest> getShortBy(ComparatorStatus comparatorStatus) {
        return historyDao.getAll().stream()
                .filter(history -> ChronoUnit.DAYS.between(history.getCheckInDate(),
                        LocalDate.now()) > 1)
                .filter(history ->  history.getGuest().isCheckIn())
                .sorted(comparatorMap.get(comparatorStatus))
                .map(History::getGuest)
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
