package com.kurtsevich.hotel.ui.actions.service;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeServicePrice extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(ChangeServicePrice.class.getName());

    public ChangeServicePrice(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            System.out.println("Введите новую стоимость");
            Double price = Double.parseDouble(reader.readLine());

            facade.changeServicePrice(serviceId, price);
            Service service = facade.getServiceById(serviceId);

            logger.log(Logger.Level.INFO, "Service price " + service.getName() + " changed to " + price);
        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.log(Logger.Level.WARNING, "change service price failed", e);
        }
    }
}
