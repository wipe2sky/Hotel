package com.hotel.server.api.service;

import com.hotel.server.model.Guest;
import com.hotel.server.util.comparators.ComparatorStatus;

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
