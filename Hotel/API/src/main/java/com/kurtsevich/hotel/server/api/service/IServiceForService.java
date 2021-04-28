package com.kurtsevich.hotel.server.api.service;

import com.kurtsevich.hotel.server.dto.ServiceDto;
import com.kurtsevich.hotel.server.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDto;

import java.util.List;

public interface IServiceForService {
    void addService(ServiceDto serviceDto);
    void deleteService(Integer serviceId);

    ServiceWithoutHistoriesDto getById(Integer serviceId);

    void addServiceToGuest(ServiceToGuestDto serviceToGuestDto);

    List<ServiceWithoutHistoriesDto> getSortByPrice();

    List<ServiceWithoutHistoriesDto> getAll();

    void changeServicePrice(ServiceDto serviceDto);

}
