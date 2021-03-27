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

public class AddService extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(AddService.class.getName());

    public AddService(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите название услуги");
            String name = reader.readLine();
            System.out.println("Введите стоимость");
            Float price = Float.parseFloat(reader.readLine());

            Service service = facade.addService(name, price);

            logger.log(Logger.Level.INFO, "Service \"" + service.getName() + "\" addedю");

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Add service failed", e);
        }
    }
}
