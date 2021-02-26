package com.hotel.api.service;

import com.hotel.model.Guest;
import com.hotel.util.comparators.ComparatorStatus;

import java.util.List;

public interface IGuestService {
    Guest add(String firstName, String lastName);

    Guest getById(Integer id);

    void deleteGuest(Integer id);

    List<Guest> getShortBy(ComparatorStatus comparatorStatus);

    Integer getCountGuestInHotel();

    List<Guest> getAll();

    List<Guest> getAllGuestInHotel();

}
