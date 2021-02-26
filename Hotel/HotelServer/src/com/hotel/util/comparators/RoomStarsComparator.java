package com.hotel.util.comparators;

import com.hotel.model.Room;

import java.util.Comparator;

public class RoomStarsComparator implements Comparator<Room> {
    @Override
    public int compare(Room o1, Room o2) {
        return o1.getStars().compareTo(o2.getStars());
    }
}
