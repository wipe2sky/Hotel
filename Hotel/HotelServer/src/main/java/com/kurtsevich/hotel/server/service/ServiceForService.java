package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.exceptions.DaoException;
import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.server.util.comparators.ServiceCostComparator;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ServiceForService implements IServiceForService {
    private static final Logger logger = new Logger(ServiceForService.class.getName());
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;

    @InjectByType
    public ServiceForService(IServiceDao serviceDao, IGuestDao guestDao, IHistoryDao historyDao) {
        this.serviceDao = serviceDao;
        this.guestDao = guestDao;
        this.historyDao = historyDao;
    }


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
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Delete service failed.", e);
            throw new ServiceException("Delete service failed.", e);
        }
    }

    @Override
    public Service getById(Integer serviceId) {
        try {
            return serviceDao.getById(serviceId);
        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Get by id failed.", e);
            throw new ServiceException("Get by id failed.", e);
        }
    }

    @Override
    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        try {
            Service service = serviceDao.getById(serviceId);
            History history = historyDao.getByGuest(guestDao.getById(guestId)).get(0);
            if(history.getGuest().isCheckIn()) {
                history.getServices().add(service);
                history.setCostOfService(history.getCostOfService() + service.getPrice());
                history.setCostOfLiving(history.getCostOfLiving() + service.getPrice());
                historyDao.update(history);
            } else throw new ServiceException("Add service to the guest failed. Guest doesn't stay in hotel.");

        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Add service to the guest failed.", e);
            throw new ServiceException("Add service to the guest failed.", e);
        }
    }

    @Override
    public List<Service> getSortByPrice() {
        return getAll().stream()
                .sorted(new ServiceCostComparator())
                .collect(Collectors.toList());
    }

    @Override
    public List<Service> getAll() {
        return serviceDao.getAll();
    }

    @Override
    public void changeServicePrice(Integer id, Double price) {
        try {
            Service service = getById(id);
            service.setPrice(price);
            serviceDao.update(service);
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Change service price failed.", e);
            throw new ServiceException("Change service price failed.", e);
        }
    }
}
