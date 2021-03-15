package com.hotel.ui.actions.history;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class GetGuestHistory  implements IAction {
    private static final Logger logger = new Logger(GetGuestHistory.class.getName());
    @InjectByType
    private HotelFacade facade;

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
            logger.log(Logger.Level.WARNING, "Get guest history failed", e);
        }
    }
}
