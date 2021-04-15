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
public class ChangeServicePrice extends AbstractAction implements IAction {

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

            log.info("Service price {} changed to {}", service.getName(), price);
        } catch (ServiceException | NumberFormatException | IOException e) {
            log.warn("Change service price failed", e);
        }
    }
}
