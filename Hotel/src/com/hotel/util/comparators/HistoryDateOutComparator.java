package com.hotel.util.comparators;

import com.hotel.model.History;

import java.util.Comparator;

public class HistoryDateOutComparator implements Comparator<History> {
    @Override
    public int compare(History o1, History o2) {
        return o1.getCheckOutDate().compareTo(o2.getCheckOutDate());
    }
}
