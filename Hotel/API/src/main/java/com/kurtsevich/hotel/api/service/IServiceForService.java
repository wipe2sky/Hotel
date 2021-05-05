package com.kurtsevich.hotel.api.service;

import com.kurtsevich.hotel.dto.ServiceDto;
import com.kurtsevich.hotel.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.dto.ServiceWithoutHistoriesDto;

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
