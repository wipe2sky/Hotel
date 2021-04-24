package com.kurtsevich.hotel.server.api.service;

import com.kurtsevich.hotel.server.dto.ServiceDto;
import com.kurtsevich.hotel.server.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDTO;

import java.util.List;

public interface IServiceForService {
    void addService(ServiceDto serviceDto);
    void deleteService(Integer serviceId);

    ServiceWithoutHistoriesDTO getById(Integer serviceId);

    void addServiceToGuest(ServiceToGuestDto serviceToGuestDto);

    List<ServiceWithoutHistoriesDTO> getSortByPrice();

    List<ServiceWithoutHistoriesDTO> getAll();

    void changeServicePrice(ServiceDto serviceDto);

}
