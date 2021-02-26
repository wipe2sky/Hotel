package com.hotel.ui.actions.history;

import com.hotel.exceptions.ServiceException;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.ui.actions.guest.GetCountGuestInHotel;
import com.hotel.util.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetListOfGuestService extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetListOfGuestService.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя:");
            Integer guestId = Integer.parseInt(reader.readLine());

            facade.getListOfGuestService(guestId).forEach(System.out::println);
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Get list of guest service failed", e);
        }
    }
}
