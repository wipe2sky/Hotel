package com.kurtsevich.hotel.ui.actions.service;

import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeServicePrice extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(ChangeServicePrice.class);

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

            logger.info("Service price {} changed to {}",service.getName(), price);
        } catch (ServiceException | NumberFormatException | IOException e) {
            logger.warn("Change service price failed", e);
        }
    }
}
