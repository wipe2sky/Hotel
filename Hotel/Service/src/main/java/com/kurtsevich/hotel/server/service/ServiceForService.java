package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.dto.ServiceDto;
import com.kurtsevich.hotel.server.dto.ServiceToGuestDto;
import com.kurtsevich.hotel.server.dto.ServiceWithoutHistoriesDTO;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ServiceForService implements IServiceForService {
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;

    @Override
    public void addService(ServiceDto serviceDto) {
        Service service = ServiceMapper.INSTANCE.serviceDtoToService(serviceDto);
        serviceDao.save(service);
    }

    @Override
    public void deleteService(Integer serviceId) {
        try {
            serviceDao.delete(serviceDao.getById(serviceId));
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete service failed.");
        }
    }

    @Override
    public ServiceWithoutHistoriesDTO getById(Integer serviceId) {
        try {
            return ServiceMapper.INSTANCE.serviceToServiceWithoutHistoriesDTO(serviceDao.getById(serviceId));
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get by id failed.");
        }
    }

    @Override
    public void addServiceToGuest(ServiceToGuestDto serviceToGuestDto) {
        try {
            Service service = serviceDao.getById(serviceToGuestDto.getServiceId());
            History history = historyDao.getGuestHistories(guestDao.getById(serviceToGuestDto.getGuestId())).get(0);
            if (history.getGuest().isCheckIn()) {
                history.getServices().add(service);
                history.setCostOfService(history.getCostOfService() + service.getPrice());
                history.setCostOfLiving(history.getCostOfLiving() + service.getPrice());
                historyDao.update(history);
            } else {
                throw new ServiceException("Add service to the guest failed. Guest doesn't stay in hotel.");
            }
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Add service to the guest failed.");
        }
    }

    @Override
    public List<ServiceWithoutHistoriesDTO> getSortByPrice() {
        try {
            List<Service> services = serviceDao.getSortByPrice();
            List<ServiceWithoutHistoriesDTO> servicesDto = new ArrayList<>();
            services.forEach(service -> servicesDto.add(ServiceMapper.INSTANCE.serviceToServiceWithoutHistoriesDTO(service)));
            return servicesDto;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sorting room failed.");
        }

    }

    @Override
    public List<ServiceWithoutHistoriesDTO> getAll() {
        try {
            List<Service> services = serviceDao.getAll();
            List<ServiceWithoutHistoriesDTO> servicesDto = new ArrayList<>();
            services.forEach(service -> servicesDto.add(ServiceMapper.INSTANCE.serviceToServiceWithoutHistoriesDTO(service)));
            return servicesDto;
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    public void changeServicePrice(ServiceDto serviceDto) {
        try {
            Service service = serviceDao.getById(serviceDto.getId());
            service.setPrice(serviceDto.getPrice());
            serviceDao.update(service);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Change service price failed.");
        }
    }
}
