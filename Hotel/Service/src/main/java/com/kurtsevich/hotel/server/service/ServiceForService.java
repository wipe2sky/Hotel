package com.kurtsevich.hotel.server.service;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.api.dao.IGuestDao;
import com.kurtsevich.hotel.server.api.dao.IHistoryDao;
import com.kurtsevich.hotel.server.api.dao.IServiceDao;
import com.kurtsevich.hotel.server.api.exceptions.DaoException;
import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.api.service.IServiceForService;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.DBConnector;
import com.kurtsevich.hotel.server.util.comparators.ServiceCostComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ServiceForService implements IServiceForService {
    private final Logger logger = LoggerFactory.getLogger(ServiceForService.class);
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;
    private final DBConnector connector;


    @InjectByType
    public ServiceForService(IServiceDao serviceDao, IGuestDao guestDao, IHistoryDao historyDao, DBConnector connector) {
        this.serviceDao = serviceDao;
        this.guestDao = guestDao;
        this.historyDao = historyDao;
        this.connector = connector;
    }


    @Override
    public Service addService(String name, Double price) {
        Service service = new Service(name, price);
        connector.startTransaction();
        serviceDao.save(service);
        connector.finishTransaction();

        return service;
    }

    @Override
    public void deleteService(Integer serviceId) {
        try {
            connector.startTransaction();
            serviceDao.delete(serviceDao.getById(serviceId));
            connector.finishTransaction();
        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Delete service failed.", e);
            throw new ServiceException("Delete service failed.", e);
        }
    }

    @Override
    public Service getById(Integer serviceId) {
        try {
            return serviceDao.getById(serviceId);
        } catch (DaoException e) {
            logger.warn("Get by id failed.", e);
            throw new ServiceException("Get by id failed.", e);
        }
    }

    @Override
    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        try {
            connector.startTransaction();

            Service service = serviceDao.getById(serviceId);
            History history = historyDao.getByGuest(guestDao.getById(guestId)).get(0);
            if(history.getGuest().isCheckIn()) {
                history.getServices().add(service);
                history.setCostOfService(history.getCostOfService() + service.getPrice());
                history.setCostOfLiving(history.getCostOfLiving() + service.getPrice());
                historyDao.update(history);
                connector.finishTransaction();
            } else {
                connector.rollback();
                throw new ServiceException("Add service to the guest failed. Guest doesn't stay in hotel.");
            }

        } catch (DaoException e) {
            connector.rollback();
            logger.warn("Add service to the guest failed.", e);
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
            connector.startTransaction();
            Service service = getById(id);
            service.setPrice(price);
            serviceDao.update(service);
            connector.finishTransaction();
        } catch (ServiceException e) {
            connector.rollback();
            logger.warn("Change service price failed.", e);
            throw new ServiceException("Change service price failed.", e);
        }
    }
}
