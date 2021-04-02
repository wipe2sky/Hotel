package com.kurtsevich.hotel.ui.actions.history;

import com.kurtsevich.hotel.server.exceptions.ServiceException;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.Guest;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetCostOfLiving extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(GetCostOfLiving.class);

    public GetCostOfLiving(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите id гостя");
            Integer guestId = Integer.parseInt(reader.readLine());

            Double cost = facade.getCostOfLiving(guestId);
            Guest guest = facade.getGuestById(guestId);

            logger.info("Cost of accommodation for Guest {} {} = {}", guest.getLastName(), guest.getFirstName(), cost);
        }catch (ServiceException | NumberFormatException |IOException e) {
            logger.warn("Get cost of living failed", e);
        }

    }
}
