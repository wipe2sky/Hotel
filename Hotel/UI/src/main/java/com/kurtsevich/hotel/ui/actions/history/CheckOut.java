package com.kurtsevich.hotel.ui.actions.history;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.server.model.History;
import com.kurtsevich.hotel.server.model.Room;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CheckOut extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(CheckOut.class);

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
            logger.info("Guest {} {} check-out from room № {} ", guest.getLastName(), guest.getFirstName(), room.getNumber());
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("Check-out failed", e);
        }
    }
}
