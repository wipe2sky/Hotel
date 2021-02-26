package com.hotel.service;

import com.hotel.api.dao.IGuestDao;
import com.hotel.api.dao.IHistoryDao;
import com.hotel.api.dao.IServiceDao;
import com.hotel.api.service.IServiceForService;
import com.hotel.dao.GuestDao;
import com.hotel.dao.HistoryDao;
import com.hotel.dao.ServiceDao;
import com.hotel.exceptions.DaoException;
import com.hotel.exceptions.ServiceException;
import com.hotel.model.Guest;
import com.hotel.model.History;
import com.hotel.model.Service;
import com.hotel.util.IdGenerator;
import com.hotel.util.comparators.ServiceCostComparator;
import com.hotel.util.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ServiceForService implements IServiceForService {
    private static final Logger logger = new Logger(ServiceForService.class.getName());

    private static ServiceForService instance;
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;


    private ServiceForService() {
        this.serviceDao = ServiceDao.getInstance();
        this.guestDao = GuestDao.getInstance();
        this.historyDao = HistoryDao.getInstance();
    }

    public static ServiceForService getInstance() {
        if (instance == null) {
            instance = new ServiceForService();
        }
        return instance;

    }

    @Override
    public Service addService(String name, Float price) {
        Service service = new Service(name, price);
        service.setId(IdGenerator.generateServiceId());
        serviceDao.save(service);
        return service;
    }

    @Override
    public void deleteService(Integer serviceId) {
        try {
            serviceDao.delete(serviceDao.getById(serviceId));
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Delete service failed.");
            throw new ServiceException("Delete service failed.");
        }
    }

    @Override
    public Service getById(Integer serviceId) {
        try {
            return serviceDao.getById(serviceId);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get by id failed.");
            throw new ServiceException("Get by id failed.");
        }
    }

    @Override
    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        try {
            Service service = serviceDao.getById(serviceId);
            Guest guest = guestDao.getById(guestId);
            History history = historyDao.getById(guestId);
            guest.getServices().add(service);
            history.setCost(history.getCost() + service.getPrice());
            history.getServices().add(service);
            guestDao.update(guest);
            historyDao.update(history);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Add service to the guest failed.");
            throw new ServiceException("Add service to the guest failed.");
        }
    }

    @Override
    public List<Service> getSortByPrice() {
        return serviceDao.getAll().stream()
                .sorted(new ServiceCostComparator())
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> getAll() {
        return serviceDao.getAll();
    }

    @Override
    public void changeServicePrice(Integer id, Float price) {
        try {
            Service service = serviceDao.getById(id);
            service.setPrice(price);
            serviceDao.update(service);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Change service price failed.");
            throw new ServiceException("Change service price failed.");
        }
    }
}
