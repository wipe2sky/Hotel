package com.hotel.server.api.service;

import com.hotel.server.model.Service;

import java.util.List;

public interface IServiceForService {
    Service addService(String name, Float price);
    void deleteService(Integer serviceId);

    Service getById(Integer serviceId);

    void addServiceToGuest(Integer serviceId, Integer guestId);

    List<Service> getSortByPrice();

    List<Service> getAll();

    void changeServicePrice(Integer id, Float price);

}
