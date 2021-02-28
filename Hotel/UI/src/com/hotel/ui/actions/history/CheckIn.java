package com.hotel.ui.actions.history;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Guest;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CheckIn extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(CheckIn.class.getName());

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

            logger.log(Logger.Level.INFO, "Guest " + guest.getLastName() + " " + guest.getFirstName()
                    + " check-in " + guest.getRoom().getNumber());
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "CheckIn Failed", e);
        }
    }
}
