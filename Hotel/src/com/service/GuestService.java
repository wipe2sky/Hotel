package com.service;

import com.api.dao.IGuestDao;
import com.api.service.IGuestService;
import com.model.Guest;
import com.util.IdGenerator;
import com.util.comparators.ComparatorStatus;
import com.util.comparators.GuestDateComparator;
import com.util.comparators.GuestLastNameComparator;

import java.util.*;

public class GuestService implements IGuestService {
    private final IGuestDao guestDao;
    private final Map<ComparatorStatus, Comparator<Guest>> comparatorMap = new HashMap<>();

    private void initMap() {
        comparatorMap.put(ComparatorStatus.DATE_CHECK_OUT, new GuestDateComparator());
        comparatorMap.put(ComparatorStatus.LAST_NAME, new GuestLastNameComparator());
    }

    public GuestService(IGuestDao guestDao) {
        this.guestDao = guestDao;
        initMap();
    }

    @Override
    public Guest addGuest(String firstName, String lastName) {
        Guest guest = new Guest(firstName, lastName);
        guest.setId(IdGenerator.generateGuestId());
        guestDao.save(guest);
        return guest;
    }

    @Override
    public void deleteById(Integer id) {
        guestDao.deleteById(id);
    }

    @Override
    public List<Guest> getShortBy(ComparatorStatus comparatorStatus) {
        List<Guest> guestsInHotel = new ArrayList<>();
        for (Guest guest :
                guestDao.getAll()) {
            if (guest.isCheckIn()) guestsInHotel.add(guest);
        }
        guestsInHotel.sort(comparatorMap.get(comparatorStatus));
        return guestsInHotel;
    }

    @Override
    public Integer getAllGuestInHotel() {
        List<Guest> guestsInHotel = new ArrayList<>(guestDao.getAll());
        for (Guest guest :
                guestDao.getAll()) {
            if (!guest.isCheckIn()) guestsInHotel.remove(guest);
        }
        return guestsInHotel.size();
    }
}
