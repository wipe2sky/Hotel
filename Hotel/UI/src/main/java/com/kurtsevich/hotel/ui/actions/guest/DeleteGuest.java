package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteGuest extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(DeleteGuest.class);

    public DeleteGuest(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя, которого необходимо удалить:");
            int guestId = Integer.parseInt(reader.readLine());
            Guest guest = facade.getGuestById(guestId);
            facade.deleteGuestById(guestId);

            logger.info("Guest " + guest.getLastName() + " " + guest.getFirstName() + " deleted");

        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("Deleting the guest failing", e);
        }
    }
}
