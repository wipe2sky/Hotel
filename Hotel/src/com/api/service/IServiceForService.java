package com.api.service;

import com.model.Service;

import java.util.List;

public interface IServiceForService {
    Service addService(String name, Float price);
    void deleteService(Integer serviceId);
    void addServiceToGuest(Integer serviceId, Integer guestId);
    List<Service> getSortByPrice();
    List<Service> getAll();

}
