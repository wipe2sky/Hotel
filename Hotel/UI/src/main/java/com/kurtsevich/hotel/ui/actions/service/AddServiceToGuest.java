package com.kurtsevich.hotel.ui.actions.service;


import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class AddServiceToGuest extends AbstractAction implements IAction {

    public AddServiceToGuest(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id услуги");
            Integer serviceId = Integer.parseInt(reader.readLine());
            System.out.println("Введите id гостя");
            Integer guestId = Integer.parseInt(reader.readLine());

            Service service = facade.getServiceById(serviceId);
            Guest guest = facade.getGuestById(guestId);

            if (guest.isCheckIn()) {
                facade.addServiceToGuest(serviceId, guestId);
                log.info("Service {} added to guest {} {}", service.getName(), guest.getLastName(), guest.getFirstName());
            } else {
                log.warn("Guest id {}  not stay in the hotel.", guest.getId());
            }
        } catch (ServiceException | NumberFormatException |IOException e) {
            log.warn("Add service to guest failed", e);
        }
    }
}
