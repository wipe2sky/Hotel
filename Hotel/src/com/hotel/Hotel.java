package com.hotel;

import com.hotel.api.service.IGuestService;
import com.hotel.api.service.IHistoryService;
import com.hotel.api.service.IRoomService;
import com.hotel.api.service.IServiceForService;
import com.hotel.model.Guest;
import com.hotel.model.History;
import com.hotel.model.Room;
import com.hotel.service.GuestService;
import com.hotel.service.HistoryService;
import com.hotel.service.RoomService;
import com.hotel.service.ServiceForService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private static final IGuestService guestService = GuestService.getInstance();

    private static final IRoomService roomService = RoomService.getInstance();

    private static final IHistoryService historyService = HistoryService.getInstance();

    private static final IServiceForService serviceForService = ServiceForService.getInstance();

    public static void main(String[] args) {

        Guest guest1 = guestService.add("G1", "g1");
        Guest guest2 = guestService.add("G2", "g2");
        Guest guest3 = guestService.add("G3", "g3");
        Guest guest4 = guestService.add("G4", "g4");

        Room room1 = roomService.addRoom(12, 3, 3, 20F);
        Room room2 = roomService.addRoom(22, 1, 5, 10F);
        Room room3 = roomService.addRoom(33, 1, 5, 100F);
        Room room4 = roomService.addRoom(13, 5, 4, 50F);
        Room room5 = roomService.addRoom(6, 2, 2, 40F);

        historyService.checkIn(guest1.getId(), room3.getId(), LocalDate.of(2021, 04, 17), LocalDate.of(2021, 04, 23));
        historyService.checkOut(1);
        historyService.checkIn(guest2.getId(), room3.getId(), LocalDate.of(2021, 02, 17), LocalDate.of(2021, 02, 19));
        historyService.checkOut(2);
        historyService.checkIn(guest3.getId(), room3.getId(), LocalDate.of(2021, 05, 17), LocalDate.of(2021, 05, 25));
        historyService.checkOut(3);
        historyService.checkIn(guest4.getId(), room3.getId(), LocalDate.of(2021, 03, 17), LocalDate.of(2021, 03, 21));
        historyService.checkOut(4);

//        System.out.println(serviceForService.getAll());
//        System.out.println("----------------");
//        System.out.println(serviceForService.getSortByPrice());
//        System.out.println(historyService.getAll());
        List<History> histories = new ArrayList<>(historyService.getLast3GuestInRoom(3));
        histories.forEach(history -> {
            System.out.print(history.getGuest());
            System.out.println(history.getCheckInDate());
            System.out.println(history.getCheckOutDate());
        });

        //        System.out.println(room1.getGuests());
//        System.out.println(room2.getGuests());
//        System.out.println(room3.getGuests());

//        System.out.println(roomService.getSortRoomBy(ComparatorStatus.PRICE, RoomStatus.BUSY));
//        System.out.println(roomService.getSortRoomBy(ComparatorStatus.CAPACITY, RoomStatus.BUSY));
//        System.out.println(roomService.getSortRoomBy(ComparatorStatus.STARS, RoomStatus.BUSY));
//        System.out.println(roomService.getSortRoomBy(ComparatorStatus.PRICE, RoomStatus.FREE));
//        System.out.println(roomService.getSortRoomBy(ComparatorStatus.CAPACITY, RoomStatus.FREE));
//        System.out.println(roomService.getSortRoomBy(ComparatorStatus.STARS, RoomStatus.FREE));
//        System.out.println(roomService.getNumberOfFreeRoom());
//
//        System.out.println(guestService.getShortGuestBy(ComparatorStatus.DATE_CHECK_OUT));
//        System.out.println(guestService.getShortGuestBy(ComparatorStatus.LAST_NAME));
//
//        System.out.println("__________________");
//        System.out.println(guestService.getAllGuestInHotel());



//        room1.setStatus(RoomStatus.ON_REPAIR);
//        System.out.println(room1);
//        room1.setStatus(RoomStatus.FREE);
//        room1.setIsCleaning(true);
//        room1.setPrice(18.5F);
//        System.out.println(room1);
//
//        Service service1 = serviceForService.addService("Stirka", 5.5F);
//        System.out.println(service1);
//        serviceForService.addServiceToGuest(1, 2);
//        serviceForService.addServiceToGuest(1, 2);
//        System.out.println(guest2);
//        historyService.checkOut(2);
//        System.out.println("__________________");
//        System.out.println(guestService.getAllGuestInHotel());
//        System.out.println("_________________________________");

//        System.out.println(roomService.getRoomsBeAvailableAfterDate(LocalDate.of(2021, 02, 22)));
//        System.out.println(guestDao.getAll());
//        System.out.println( guestService.getShortGuestByDateCheckOut());
//        System.out.println("_________________________________");

//        serviceForService.addServiceToGuest(1,4);
//        serviceForService.addServiceToGuest(2,4);
//        serviceForService.addServiceToGuest(1,4);
//        System.out.println(historyService.getListOfGuestService(4));
//        System.out.println(historyService.getCostOfLiving(4));
    }
}
