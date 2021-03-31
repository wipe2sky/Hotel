package com.kurtsevich.hotel.server.util.comparators;

import com.kurtsevich.hotel.server.model.History;

import java.util.Comparator;

public class GuestLastNameComparator implements Comparator<History> {

    @Override
    public int compare(History o1, History o2) {
        return o1.getGuest().getLastName().compareTo(o2.getGuest().getLastName());
    }
}
