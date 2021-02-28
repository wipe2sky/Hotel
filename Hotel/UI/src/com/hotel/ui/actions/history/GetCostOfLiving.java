package com.hotel.ui.actions.history;

import com.hotel.exceptions.ServiceException;
import com.hotel.model.Guest;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetCostOfLiving extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetCostOfLiving.class.getName());

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя");
            Integer guestId = Integer.parseInt(reader.readLine());

            Float cost = facade.getCostOfLiving(guestId);
            Guest guest = facade.getGuestById(guestId);

            logger.log(Logger.Level.INFO, "Cost of accommodation for Guest " + guest.getLastName() + " " + guest.getFirstName() +" = " + cost);
        }catch (ServiceException | NumberFormatException |IOException e) {
            logger.log(Logger.Level.WARNING, "Get cost of living failed", e);
        }

    }
}
