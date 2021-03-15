package com.hotel.ui.actions.service;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.Guest;
import com.hotel.server.model.Service;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class AddServiceToGuest  implements IAction {
    private static final Logger logger = new Logger(AddServiceToGuest.class.getName());
    @InjectByType
    private HotelFacade facade;
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

            if(guest.isCheckIn()) {
                facade.addServiceToGuest(serviceId, guestId);
                logger.log(Logger.Level.INFO, "Service " + service.getName()
                        + " added to guest " + guest.getLastName() + " " + guest.getFirstName());
            } else                 logger.log(Logger.Level.WARNING, "Guest id "+ guest.getId()+ " not stay in the hotel.");
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Add service to guest failed", e);
        }
    }
}
