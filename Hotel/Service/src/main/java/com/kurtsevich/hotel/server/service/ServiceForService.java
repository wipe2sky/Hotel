package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

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
    public Service addService(String name, Double price) {
        Service service = new Service(name, price);
        serviceDao.save(service);

        return service;
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
    public Service getById(Integer serviceId) {
        try {
            return serviceDao.getById(serviceId);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get by id failed.");
        }
    }

    @Override
    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        try {
            Service service = serviceDao.getById(serviceId);
            History history = historyDao.getGuestHistories(guestDao.getById(guestId)).get(0);
            if(history.getGuest().isCheckIn()) {
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
    public List<Service> getSortByPrice() {
        try {
            return serviceDao.getSortByPrice();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sorting room failed.");
        }

    }

    @Override
    public List<Service> getAll() {
        try {
            return serviceDao.getAll();
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    public void changeServicePrice(Integer id, Double price) {
        try {
            Service service = serviceDao.getById(id);
            service.setPrice(price);
            serviceDao.update(service);
        } catch (DaoException e) {
            log.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Change service price failed.");
        }
    }
}
