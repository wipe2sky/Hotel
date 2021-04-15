package com.kurtsevich.hotel.ui.actions.history;

import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Log4j2
public class CheckOut extends AbstractAction implements IAction {

    public CheckOut(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя:");
            Integer guestId = Integer.parseInt(reader.readLine());
            History history = facade.getGuestHistory(guestId).get(0);
            Guest guest = history.getGuest();
            Room room = history.getRoom();

            facade.checkOut(guestId);
            log.info("Guest {} {} check-out from room № {} ", guest.getLastName(), guest.getFirstName(), room.getNumber());
        } catch (ServiceException | NumberFormatException |IOException e) {
            log.warn("Check-out failed", e);
        }
    }
}
