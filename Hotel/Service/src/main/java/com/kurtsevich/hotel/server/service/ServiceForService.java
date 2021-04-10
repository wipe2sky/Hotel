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
import com.kurtsevich.hotel.server.util.HibernateConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class ServiceForService implements IServiceForService {
    private final Logger logger = LoggerFactory.getLogger(ServiceForService.class);
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;
    private final HibernateConnector connector;


    @InjectByType
    public ServiceForService(IServiceDao serviceDao, IGuestDao guestDao, IHistoryDao historyDao, HibernateConnector connector) {
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
        } catch (ServiceException e) {
            connector.rollbackTransaction();
            logger.warn("Delete service failed.", e);
            throw new ServiceException("Delete service failed.", e);
        }finally {
            connector.finishTransaction();
        }
    }

    @Override
    public Service getById(Integer serviceId) {
        try {
            connector.startTransaction();
            return serviceDao.getById(serviceId);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get by id failed.", e);
            throw new ServiceException("Get by id failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        try {
            Service service = serviceDao.getById(serviceId);
            History history = historyDao.getByGuest(guestDao.getById(guestId)).get(0);
            if(history.getGuest().isCheckIn()) {
                connector.startTransaction();
                history.getServices().add(service);
                history.setCostOfService(history.getCostOfService() + service.getPrice());
                history.setCostOfLiving(history.getCostOfLiving() + service.getPrice());
                historyDao.update(history);
            } else {
                throw new ServiceException("Add service to the guest failed. Guest doesn't stay in hotel.");
            }
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Add service to the guest failed.", e);
            throw new ServiceException("Add service to the guest failed.", e);
        }finally {
            connector.finishTransaction();
        }
    }

    @Override
    public List<Service> getSortByPrice() {
        try {
            connector.startTransaction();
            return serviceDao.getSortByPrice();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Sorting room failed.", e);
            throw new ServiceException("Sorting room failed.", e);
        } finally {
            connector.finishTransaction();
        }

    }

    @Override
    public List<Service> getAll() {
        try {
            connector.startTransaction();
            return serviceDao.getAll();
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Get rooms failed.", e);
            throw new ServiceException("Get rooms failed.", e);
        } finally {
            connector.finishTransaction();
        }
    }

    @Override
    public void changeServicePrice(Integer id, Double price) {
        try {
            connector.startTransaction();
            Service service = serviceDao.getById(id);
            service.setPrice(price);
            serviceDao.update(service);
        } catch (DaoException e) {
            connector.rollbackTransaction();
            logger.warn("Change service price failed.", e);
            throw new ServiceException("Change service price failed.", e);
        }finally {
            connector.finishTransaction();
        }
    }
}
