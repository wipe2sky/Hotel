package com.kurtsevich.hotel.server.util.comparators;

import com.kurtsevich.hotel.server.model.Room;

import java.util.Comparator;

public class RoomPriceComparator implements Comparator<Room> {
    @Override
    public int compare(Room o1, Room o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
