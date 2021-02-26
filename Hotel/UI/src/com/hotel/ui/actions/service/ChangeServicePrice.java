package com.hotel.ui.actions.service;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Service;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeServicePrice extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(ChangeServicePrice.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            System.out.println("Введите новую стоимость");
            Float price = Float.parseFloat(reader.readLine());

            facade.changeServicePrice(serviceId, price);
            Service service = facade.getServiceById(serviceId);

            logger.log(Logger.Level.INFO, "Service price " + service.getName() + " changed to " + price);
        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.log(Logger.Level.WARNING, "change service price failed", e);
        }
    }
}
