package com.hotel.ui.actions.guest;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.logger.Logger;

public class GetCountGuestInHotel extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetCountGuestInHotel.class.getName());

    @Override
    public void execute() {
       Integer countGuestInHotel = facade.getCountGuestInHotel();
       logger.log(Logger.Level.INFO, "In Hotel stay " + countGuestInHotel + " guests, at this moment.");
    }
}
