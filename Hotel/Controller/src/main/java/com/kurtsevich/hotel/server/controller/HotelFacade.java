package com.kurtsevich.hotel.server.controller;

import com.kurtsevich.hotel.server.api.service.IGuestService;
import com.kurtsevich.hotel.server.api.service.IHistoryService;
import com.kurtsevich.hotel.server.api.service.IRoomService;
import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.model.*;
import com.kurtsevich.hotel.server.service.GuestService;
import com.kurtsevich.hotel.server.service.HistoryService;
import com.kurtsevich.hotel.server.service.RoomService;
import com.kurtsevich.hotel.server.service.ServiceForService;
import com.kurtsevich.hotel.server.util.SortStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public class HotelFacade {
    private final IGuestService guestService;
    private final IRoomService roomService;
    private final IHistoryService historyService;
    private final IServiceForService serviceForService;

    @Autowired
    public HotelFacade(IGuestService guestService, IRoomService roomService, IHistoryService historyService, IServiceForService serviceForService) {
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

    public Long getCountGuestInHotel() {
        return guestService.getCountGuestInHotel();
    }

    public List<Guest> getAllGuest() {
        return guestService.getAll();
    }

    public List<Guest> getAllGuestInHotel() {
        return guestService.getAllGuestInHotel();
    }

    public List<Guest> getGuestSortBy(SortStatus sortStatus) {
        return guestService.getSortBy(sortStatus);

    }

    public Room addRoom(Integer number, Integer capacity, Integer stars, Double price) {
        return roomService.addRoom(number, capacity, stars, price);
    }

    public void deleteRoom(Integer id) {
        roomService.deleteRoom(id);
    }

    public List<Room> getRoomAvailableAfterDate(LocalDateTime date) {
        return roomService.getAvailableAfterDate(date);
    }

    public Room getRoomInfo(Integer id) {
        return roomService.getById(id);
    }
    public List<History> getRoomHistory(Integer id){
        return roomService.getRoomHistory(id);
    }

    public void changeRoomPrice(Integer id, Double price) {
        roomService.changePrice(id, price);
    }

    public Integer getNumberOfFree() {
        return roomService.getNumberOfFree();
    }

    public List<Room> getRoomSortBy(SortStatus sortStatus, RoomStatus roomStatus) {
        return roomService.getSortBy(sortStatus, roomStatus);
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

    public Double getCostOfLiving(Integer roomId) {
        return historyService.getCostOfLiving(roomId);
    }

    public List<History> getGuestHistory (Integer id){
        return historyService.getGuestHistory(id);
    }

    public List<Guest> getLast3GuestInRoom(Integer roomId) {
        return historyService.getLast3GuestInRoom(roomId);
    }

    public List<History> getAllHistories() {
        return historyService.getAll();
    }

    public List<Service> getListOfGuestService(Integer guestId) {
        return historyService.getListOfGuestService(guestId);
    }

    public Service addService(String name, Double price) {
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

    public void changeServicePrice(Integer id, Double price) {
        serviceForService.changeServicePrice(id, price);
    }
}
