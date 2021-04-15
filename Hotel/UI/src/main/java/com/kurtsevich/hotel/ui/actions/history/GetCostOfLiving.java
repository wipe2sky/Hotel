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
public class GetCostOfLiving extends AbstractAction implements IAction {

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

            log.info("Cost of accommodation for Guest {} {} = {}", guest.getLastName(), guest.getFirstName(), cost);
        }catch (ServiceException | NumberFormatException |IOException e) {
            log.warn("Get cost of living failed", e);
        }

    }
}
