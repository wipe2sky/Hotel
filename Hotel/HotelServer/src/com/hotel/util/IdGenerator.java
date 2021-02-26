package com.hotel.util;

public class IdGenerator {
    private static Integer guestId = 1;
    private static Integer roomId = 1;
    private static Integer serviceId = 1;
    private static Integer historyId = 1;

    private IdGenerator() {
    }

    public static Integer generateGuestId() {
        return guestId++;
    }

    public static Integer generateRoomId() {
        return roomId++;
    }

    public static Integer generateServiceId() {
        return serviceId++;
    }
    public static Integer generateHistoryId(){
        return historyId++;
    }

}
