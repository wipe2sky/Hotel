package com.kurtsevich.hotel.ui.actions.history;

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
public class CheckIn extends AbstractAction implements IAction {

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

            log.info("Guest {} {} check-in {}", guest.getLastName(), guest.getFirstName(), facade.getRoomInfo(roomId).getNumber());
        } catch (ServiceException | NumberFormatException |IOException e) {
            log.warn("CheckIn Failed", e);
        }
    }
}
