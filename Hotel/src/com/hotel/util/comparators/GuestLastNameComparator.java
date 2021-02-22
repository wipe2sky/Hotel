package com.hotel.util.comparators;

import com.hotel.model.Guest;

import java.util.Comparator;

public class GuestLastNameComparator implements Comparator<Guest> {

    @Override
    public int compare(Guest o1, Guest o2) {
        return o1.getLastName().compareTo(o2.getLastName());
    }
}
