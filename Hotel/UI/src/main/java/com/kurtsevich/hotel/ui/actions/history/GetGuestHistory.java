package com.kurtsevich.hotel.ui.actions.history;

import com.kurtsevich.hotel.server.api.exceptions.ServiceException;
import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetGuestHistory extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(GetGuestHistory.class);

    public GetGuestHistory(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя");
            Integer guestId = Integer.parseInt(reader.readLine());
            System.out.println();
            facade.getGuestHistory(guestId).forEach(System.out::println);
            System.out.println();
        } catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("Get guest history failed", e);
        }
    }
}
