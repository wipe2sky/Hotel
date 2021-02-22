package com.hotel.service;

import com.hotel.api.dao.IGuestDao;
import com.hotel.api.service.IGuestService;
import com.hotel.dao.GuestDao;
import com.hotel.model.Guest;
import com.hotel.util.IdGenerator;
import com.hotel.util.comparators.ComparatorStatus;
import com.hotel.util.comparators.GuestDateComparator;
import com.hotel.util.comparators.GuestLastNameComparator;

import java.util.*;
import java.util.stream.Collectors;

public class GuestService implements IGuestService {
    private static GuestService instance;
    private final IGuestDao guestDao;
    private final Map<ComparatorStatus, Comparator<Guest>> comparatorMap = new HashMap<>();

    private void initMap() {
        comparatorMap.put(ComparatorStatus.DATE_CHECK_OUT, new GuestDateComparator());
        comparatorMap.put(ComparatorStatus.LAST_NAME, new GuestLastNameComparator());
    }

    private GuestService() {
        this.guestDao = GuestDao.getInstance();
        initMap();
    }

    public static GuestService getInstance() {
        if(instance == null) instance = new GuestService();
        return instance;
    }

    @Override
    public Guest add(String firstName, String lastName) {
        Guest guest = new Guest(firstName, lastName);
        guest.setId(IdGenerator.generateGuestId());
        guestDao.save(guest);
        return guest;
    }

    @Override
    public Guest getById(Integer id) {
        return guestDao.getById(id);
    }

    @Override
    public void deleteGuest(Integer id) {
        guestDao.delete(guestDao.getById(id));
    }

    @Override
    public List<Guest> getShortBy(ComparatorStatus comparatorStatus) {
        return guestDao.getAll().stream()
                .filter(Guest::isCheckIn)
                .sorted(comparatorMap.get(comparatorStatus))
                .collect(Collectors.toList());
    }

        @Override
        public Integer getCountGuestInHotel() {
            List<Guest> guestsInHotel = guestDao.getAll().stream()
                    .filter(Guest::isCheckIn)
                    .collect(Collectors.toList());
            return guestsInHotel.size();
    }

    @Override
    public List<Guest> getAll() {
        return guestDao.getAll();
    }

    @Override
    public List<Guest> getAllGuestInHotel() {
        return guestDao.getAll().stream()
                .filter(Guest::isCheckIn)
                .collect(Collectors.toList());
    }
}
