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

public class AddService extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(AddService.class);

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
            Double price = Double.parseDouble(reader.readLine());

            Service service = facade.addService(name, price);

            logger.info("Service \"{}\" added",service.getName());

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("Add service failed", e);
        }
    }
}
