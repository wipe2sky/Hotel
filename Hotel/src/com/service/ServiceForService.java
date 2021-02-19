package com.service;

import com.api.dao.IGuestDao;
import com.api.dao.IHistoryDao;
import com.api.dao.IServiceDao;
import com.api.service.IServiceForService;
import com.model.Guest;
import com.model.History;
import com.model.Service;
import com.util.IdGenerator;
import com.util.comparators.ServiceCostComparator;

import java.util.List;

public class ServiceForService implements IServiceForService {
    private final IServiceDao serviceDao;
    private final IGuestDao guestDao;
    private final IHistoryDao historyDao;


    public ServiceForService(IServiceDao serviceDao, IGuestDao guestDao, IHistoryDao historyDao) {
        this.serviceDao = serviceDao;
        this.guestDao = guestDao;
        this.historyDao = historyDao;
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
        serviceDao.deleteById(serviceId);
    }

    @Override
    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        Service service = serviceDao.getById(serviceId);
        Guest guest = guestDao.getById(guestId);
        History history = historyDao.getById(guestId);
        guest.getServices().add(service);
        history.setCost(history.getCost() + service.getPrice());
        guestDao.update(guest);
        historyDao.update(history);
    }

    @Override
    public List<Service> getSortByPrice() {
        List<Service> services = serviceDao.getAll();
        services.sort(new ServiceCostComparator());
        return services;
    }

    @Override
    public List<Service> getAll() {
        return serviceDao.getAll();
    }
}
