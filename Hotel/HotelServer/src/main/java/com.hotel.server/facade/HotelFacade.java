package com.hotel.server.facade;

import com.hotel.factory.ObjectFactory;
import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.model.*;
import com.hotel.server.service.GuestService;
import com.hotel.server.service.HistoryService;
import com.hotel.server.service.RoomService;
import com.hotel.server.service.ServiceForService;
import com.hotel.server.util.comparators.ComparatorStatus;

import java.time.LocalDate;
import java.util.List;
@Singleton
public class HotelFacade {
//    private static HotelFacade instance;
    @InjectByType
    private static GuestService guestService;
    @InjectByType
    private static  RoomService roomService;
    @InjectByType
    private static  HistoryService historyService;
    @InjectByType
    private static  ServiceForService serviceForService;
//    private static final GuestService guestService = GuestService.getInstance();
//    private static final RoomService roomService = RoomService.getInstance();
//    private static final HistoryService historyService = HistoryService.getInstance();
//    private static final ServiceForService serviceForService = ServiceForService.getInstance();


    public HotelFacade() {
    }

//    public static HotelFacade getInstance() {
//        if (instance == null) instance = new HotelFacade();
//        return instance;
//    }

    public Guest addGuest(String firstName, String lastName) {
        return guestService.add(firstName, lastName);
    }

    public Guest getGuestById(Integer id) {
        return guestService.getById(id);
    }

    public void deleteGuestById(Integer id) {
        guestService.deleteGuest(id);
    }

    public Integer getCountGuestInHotel() {
        return guestService.getCountGuestInHotel();
    }

    public List<Guest> getAllGuest() {
        return guestService.getAll();
    }

    public List<Guest> getAllGuestInHotel() {
        return guestService.getAllGuestInHotel();
    }

    public List<Guest> getGuestSortBy(ComparatorStatus comparatorStatus) {
        return guestService.getShortBy(comparatorStatus);

    }

    public Room addRoom(Integer number, Integer capacity, Integer stars, Float price) {
        return roomService.addRoom(number, capacity, stars, price);
    }

    public void deleteRoom(Integer id) {
        roomService.deleteRoom(id);
    }

    public List<Room> getRoomAvailableAfterDate(LocalDate date) {
        return roomService.getAvailableAfterDate(date);
    }

    public Room getRoomInfo(Integer id) {
        return roomService.getInfo(id);
    }
    public List<History> getRoomHistory(Integer id){
        return roomService.getRoomHistory(id);
    }

    public void changeRoomPrice(Integer id, Float price) {
        roomService.changePrice(id, price);
    }

    public Integer getNumberOfFree() {
        return roomService.getNumberOfFree();
    }

    public List<Room> getRoomSortBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        return roomService.getSortBy(comparatorStatus, roomStatus);
    }

    public void setRoomCleaningStatus(Integer id, boolean isCleaning) {
        roomService.setCleaningStatus(id, isCleaning);
    }

    public void setRoomRepairStatus(Integer id, boolean isRepair) {
        roomService.setRepairStatus(id, isRepair);
    }

    public List<Room> getAllRoom() {
        return roomService.getAll();
    }

    public void checkIn(Integer guestId, Integer roomId, Integer daysStay) {
        historyService.checkIn(guestId, roomId, daysStay);
    }

    public void checkOut(Integer guestId) {
        historyService.checkOut(guestId);
    }

    public Float getCostOfLiving(Integer roomId) {
        return historyService.getCostOfLiving(roomId);
    }

    public List<History> getGuestHistory (Integer id){
        return historyService.getGuestHistory(id);
    }

    public List<History> getLast3GuestInRoom(Integer roomId) {
        return historyService.getLast3GuestInRoom(roomId);
    }

    public List<History> getAllHistories() {
        return historyService.getAll();
    }

    public List<Service> getListOfGuestService(Integer guestId) {
        return historyService.getListOfGuestService(guestId);
    }

    public Service addService(String name, Float price) {
        return serviceForService.addService(name, price);
    }

    public void deleteService(Integer serviceId) {
        serviceForService.deleteService(serviceId);
    }

    public Service getServiceById(Integer id) {
        return serviceForService.getById(id);
    }

    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        serviceForService.addServiceToGuest(serviceId, guestId);
    }

    public List<Service> getServiceSortByPrice() {
        return serviceForService.getSortByPrice();
    }

    public List<Service> getAllService() {
        return serviceForService.getAll();
    }

    public void changeServicePrice(Integer id, Float price) {
        serviceForService.changeServicePrice(id, price);
    }
}