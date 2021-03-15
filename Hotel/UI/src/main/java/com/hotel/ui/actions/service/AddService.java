package com.hotel.ui.actions.service;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.Service;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class AddService  implements IAction {
    private static final Logger logger = new Logger(AddService.class.getName());
    @InjectByType
    private HotelFacade facade;
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
