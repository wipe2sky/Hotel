package com.hotel.service;

import com.hotel.api.dao.IGuestDao;
import com.hotel.api.dao.IHistoryDao;
import com.hotel.api.dao.IServiceDao;
import com.hotel.api.service.IServiceForService;
import com.hotel.dao.GuestDao;
import com.hotel.dao.HistoryDao;
import com.hotel.dao.ServiceDao;
import com.hotel.model.Guest;
import com.hotel.model.History;
import com.hotel.model.Service;
import com.hotel.util.IdGenerator;
import com.hotel.util.comparators.ServiceCostComparator;

import java.util.List;

public class ServiceForService implements IServiceForService {
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
        if (instance == null) instance = new ServiceForService();
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
        serviceDao.delete(serviceDao.getById(serviceId));
    }

    @Override
    public Service getById(Integer serviceId) {
        return serviceDao.getById(serviceId);
    }

    @Override
    public void addServiceToGuest(Integer serviceId, Integer guestId) {
        Service service = serviceDao.getById(serviceId);
        Guest guest = guestDao.getById(guestId);
        History history = historyDao.getById(guestId);
        guest.getServices().add(service);
        history.setCost(history.getCost() + service.getPrice());
        history.getServices().add(service);
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

    @Override
    public void changeServicePrice(Integer id, Float price) {
        Service service = serviceDao.getById(id);
        service.setPrice(price);
        serviceDao.update(service);
    }
}
