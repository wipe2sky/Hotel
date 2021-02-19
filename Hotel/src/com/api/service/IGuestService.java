package com.api.service;

import com.model.Guest;
import com.util.comparators.ComparatorStatus;

import java.util.List;

public interface IGuestService {
    Guest addGuest(String firstName, String lastName);

    void deleteById(Integer id);

    List<Guest> getShortBy(ComparatorStatus comparatorStatus);

    Integer getAllGuestInHotel();

}
