package com.kurtsevich.hotel.server.util.comparators;

import com.kurtsevich.hotel.server.model.Guest;

import java.util.Comparator;

public class GuestDateComparator implements Comparator<Guest> {
    @Override
    public int compare(Guest o1, Guest o2) {
        return o1.getLastHistory().getCheckOutDate().compareTo(o2.getLastHistory().getCheckOutDate());
    }
}
