package com.kurtsevich.hotel.ui.actions.service;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteService extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(DeleteService.class);

    public DeleteService(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            Service service = facade.getServiceById(serviceId);

            facade.deleteService(serviceId);

            logger.info("Service {} deleted.", service.getName());
        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.warn("Delete Service failed", e);
        }
    }
}
