package com.hotel.server.service;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.api.dao.IGuestDao;
import com.hotel.server.api.dao.IHistoryDao;
import com.hotel.server.api.dao.IServiceDao;
import com.hotel.server.api.service.IServiceForService;
import com.hotel.server.exceptions.DaoException;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.model.Guest;
import com.hotel.server.model.History;
import com.hotel.server.model.Service;
import com.hotel.server.util.IdGenerator;
import com.hotel.server.util.comparators.ServiceCostComparator;
import com.hotel.server.util.Logger;

import java.util.List;
import java.util.stream.Collectors;
@Singleton
public class ServiceForService implements IServiceForService {
    private static final Logger logger = new Logger(ServiceForService.class.getName());

//    private static ServiceForService instance;
//    @InjectByType
    private IServiceDao serviceDao;
//    @InjectByType
    private IGuestDao guestDao;
//    @InjectByType
    private  IHistoryDao historyDao;
//    @InjectByType
    private IdGenerator idGenerator;
@InjectByType
    public ServiceForService(IServiceDao serviceDao, IGuestDao guestDao, IHistoryDao historyDao, IdGenerator idGenerator) {
        this.serviceDao = serviceDao;
        this.guestDao = guestDao;
        this.historyDao = historyDao;
        this.idGenerator = idGenerator;
    }

    //    private ServiceForService() {
////        this.serviceDao = ServiceDao.getInstance();
////        this.guestDao = GuestDao.getInstance();
////        this.historyDao = HistoryDao.getInstance();
//    }

//    public static ServiceForService getInstance() {
//        if (instance == null) {
//            instance = new ServiceForService();
//        }
//        return instance;
//
//    }

    @Override
    public Service addService(String name, Float price) {
        Service service = new Service(name, price);
//        service.setId(IdGenerator.getInstance().generateServiceId());
        service.setId(idGenerator.generateServiceId());
        serviceDao.save(service);
        return service;
    }

    @Override
    public void deleteService(Integer serviceId) {
        try {
            serviceDao.delete(getById(serviceId));
        } catch (ServiceException e) {
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
            if(guest.isCheckIn()) {
                guest.getServices().add(service);
                history.setCostOfService(history.getCostOfService() + service.getPrice());
                history.getServices().add(service);
                guestDao.update(guest);
                historyDao.update(history);
            } else throw new ServiceException("Add service to the guest failed. Guest doesn't stay in hotel.");

        } catch (DaoException e) {
            logger.log(Logger.Level.WARNING, "Add service to the guest failed.");
            throw new ServiceException("Add service to the guest failed.");
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
    public void changeServicePrice(Integer id, Float price) {
        try {
            Service service = getById(id);
            service.setPrice(price);
            serviceDao.update(service);
        } catch (ServiceException e) {
            logger.log(Logger.Level.WARNING, "Change service price failed.");
            throw new ServiceException("Change service price failed.");
        }
    }
}
