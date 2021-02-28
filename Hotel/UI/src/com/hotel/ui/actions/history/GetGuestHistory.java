package com.hotel.ui.actions.history;

import com.hotel.exceptions.ServiceException;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetGuestHistory extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetGuestHistory.class.getName());

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
