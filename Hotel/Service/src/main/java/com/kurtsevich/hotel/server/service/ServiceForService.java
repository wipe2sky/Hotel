package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ServiceForService implements IServiceForService {
    private final Logger logger = LoggerFactory.getLogger(ServiceForService.class);
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;


    @Autowired
    public ServiceForService(IServiceDao serviceDao, IGuestDao guestDao, IHistoryDao historyDao) {
        this.serviceDao = serviceDao;
        this.guestDao = guestDao;
        this.historyDao = historyDao;
    }


    @Override
    @Transactional
    public Service addService(String name, Double price) {
        Service service = new Service(name, price);
        serviceDao.save(service);

        return service;
    }

    @Override
    @Transactional
    public void deleteService(Integer serviceId) {
        try {
            serviceDao.delete(serviceDao.getById(serviceId));
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Delete service failed.");
        }
    }

    @Override
    @Transactional
    public Service getById(Integer serviceId) {
        try {
            return serviceDao.getById(serviceId);
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get by id failed.");
        }
    }

    @Override
    @Transactional
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
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Add service to the guest failed.");
        }
    }

    @Override
    @Transactional
    public List<Service> getSortByPrice() {
        try {
            return serviceDao.getSortByPrice();
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Sorting room failed.");
        }

    }

    @Override
    @Transactional
    public List<Service> getAll() {
        try {
            return serviceDao.getAll();
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Get rooms failed.");
        }
    }

    @Override
    @Transactional
    public void changeServicePrice(Integer id, Double price) {
        try {
            Service service = serviceDao.getById(id);
            service.setPrice(price);
            serviceDao.update(service);
        } catch (DaoException e) {
            logger.warn(e.getLocalizedMessage(), e);
            throw new ServiceException("Change service price failed.");
        }
    }
}
