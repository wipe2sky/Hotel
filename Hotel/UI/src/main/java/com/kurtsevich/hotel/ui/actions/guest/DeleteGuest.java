package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class DeleteGuest extends AbstractAction implements IAction {

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

            log.info("Guest {} {} deleted", guest.getLastName(), guest.getFirstName());

        } catch (ServiceException | NumberFormatException |IOException e) {
            log.warn("Deleting the guest failing", e);
        }
    }
}
