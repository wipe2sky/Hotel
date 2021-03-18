package com.kurtsevich.hotel.server.facade;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.model.*;
import com.kurtsevich.hotel.server.service.GuestService;
import com.kurtsevich.hotel.server.service.HistoryService;
import com.kurtsevich.hotel.server.service.RoomService;
import com.kurtsevich.hotel.server.service.ServiceForService;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;

import java.time.LocalDate;
import java.util.List;
@Singleton
public class HotelFacade {
    private final  GuestService guestService;
    private final RoomService roomService;
    private final HistoryService historyService;
    private final ServiceForService serviceForService;

    @InjectByType
    public HotelFacade(GuestService guestService, RoomService roomService, HistoryService historyService, ServiceForService serviceForService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.historyService = historyService;
        this.serviceForService = serviceForService;
    }


    public Guest addGuest(String lastName, String firstName) {
        return guestService.add(lastName, firstName);
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
