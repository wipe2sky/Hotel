package com.kurtsevich.hotel.server.util.comparators;

import com.kurtsevich.hotel.server.model.History;

import java.util.Comparator;

public class HistoryDateOutComparator implements Comparator<History> {
    @Override
    public int compare(History o1, History o2) {
        return o1.getCheckOutDate().compareTo(o2.getCheckOutDate());
    }
}
