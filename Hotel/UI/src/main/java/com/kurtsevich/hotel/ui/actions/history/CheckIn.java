package com.kurtsevich.hotel.ui.actions.history;

import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CheckIn extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(CheckIn.class);

    public CheckIn(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите id гостя:");
            Integer guestId = Integer.parseInt(reader.readLine());
            System.out.println("Введите id номера:");
            Integer roomId = Integer.parseInt(reader.readLine());
            System.out.println("Введите количество дней");
            Integer daysStay = Integer.parseInt(reader.readLine());

            facade.checkIn(guestId, roomId, daysStay);

            Guest guest = facade.getGuestById(guestId);

            logger.info("Guest {} {} check-in {}", guest.getLastName(), guest.getFirstName(), facade.getRoomInfo(roomId).getNumber());
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("CheckIn Failed", e);
        }
    }
}
