package com.hotel.ui.actions.history;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.exceptions.ServiceException;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.Guest;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Singleton
public class GetCostOfLiving  implements IAction {
    private static final Logger logger = new Logger(GetCostOfLiving.class.getName());
    @InjectByType
    private HotelFacade facade;

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
