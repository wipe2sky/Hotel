package com.kurtsevich.hotel.service;

import com.kurtsevich.hotel.api.dao.IGuestDao;
import com.kurtsevich.hotel.api.dao.IHistoryDao;
import com.kurtsevich.hotel.api.dao.IServiceDao;
import com.kurtsevich.hotel.api.exceptions.ServiceException;
import com.kurtsevich.hotel.api.service.IServiceForService;
import com.kurtsevich.hotel.dto.ServiceDto;
import com.kurtsevich.hotel.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.dto.ServiceWithoutHistoriesDto;
import com.kurtsevich.hotel.model.History;
import com.kurtsevich.hotel.model.Service;
import com.kurtsevich.hotel.util.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
@RequiredArgsConstructor
public class ServiceForService implements IServiceForService {
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;
    private final ServiceMapper mapper;

    @Override
    public void addService(ServiceDto serviceDto) {
        serviceDao.save(mapper.serviceDtoToService(serviceDto));
    }

    @Override
    public void deleteService(Integer serviceId) {
        serviceDao.delete(serviceDao.getById(serviceId));
    }

    @Override
    public ServiceWithoutHistoriesDto getById(Integer serviceId) {
        return mapper.serviceToServiceWithoutHistoriesDTO(serviceDao.getById(serviceId));
    }

    @Override
    public void addServiceToGuest(ServiceToGuestDto serviceToGuestDto) {
        Service service = serviceDao.getById(serviceToGuestDto.getServiceId());
        History history = historyDao.getCurrentGuestHistories(guestDao.getById(serviceToGuestDto.getGuestId()));
        if (history.getGuest().isCheckIn()) {
            history.getServices().add(service);
            history.setCostOfService(history.getCostOfService() + service.getPrice());
            history.setCostOfLiving(history.getCostOfLiving() + service.getPrice());
            historyDao.update(history);
        } else {
            throw new ServiceException("Add service to the guest failed. Guest doesn't stay in hotel.");
        }
    }

    @Override
    public List<ServiceWithoutHistoriesDto> getSortByPrice() {
        return serviceDao.getSortByPrice().stream()
                .map(mapper::serviceToServiceWithoutHistoriesDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceWithoutHistoriesDto> getAll() {
        return serviceDao.getAll().stream()
                .map(mapper::serviceToServiceWithoutHistoriesDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void changeServicePrice(ServiceDto serviceDto) {
        Service service = serviceDao.getById(serviceDto.getId());
        service.setPrice(serviceDto.getPrice());
        serviceDao.update(service);
    }
}
