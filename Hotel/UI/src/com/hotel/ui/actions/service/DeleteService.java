package com.hotel.ui.actions.service;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Service;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteService extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(DeleteService.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            Service service = facade.getServiceById(serviceId);

            facade.deleteService(serviceId);

            logger.log(Logger.Level.INFO, "Service " + service.getName() + " deleted.");
        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.log(Logger.Level.WARNING, "Delete Service failed", e);
        }
    }
}
