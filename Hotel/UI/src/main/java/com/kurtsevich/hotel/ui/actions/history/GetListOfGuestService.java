package com.kurtsevich.hotel.ui.actions.history;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetListOfGuestService extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetListOfGuestService.class.getName());

    public GetListOfGuestService(HotelFacade facade) {
        this.facade = facade;
    }

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
