package com.kurtsevich.hotel.ui.actions.service;


import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class AddService extends AbstractAction implements IAction {

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

            log.info("Service \"{}\" added", service.getName());

        } catch (ServiceException | NumberFormatException |IOException e) {
            log.warn("Add service failed", e);
        }
    }
}
