package com.kurtsevich.hotel.ui.actions.service;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.Service;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddServiceToGuest extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(AddServiceToGuest.class);

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
                logger.info("Service " + service.getName()
                        + " added to guest " + guest.getLastName() + " " + guest.getFirstName());
            } else {
                logger.warn("Guest id " + guest.getId() + " not stay in the hotel.");
            }
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("Add service to guest failed", e);
        }
    }
}
