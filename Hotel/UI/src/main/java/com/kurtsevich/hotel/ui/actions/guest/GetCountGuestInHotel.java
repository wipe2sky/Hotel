package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetCountGuestInHotel extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetCountGuestInHotel.class.getName());

    public GetCountGuestInHotel(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        Integer countGuestInHotel = facade.getCountGuestInHotel();
        logger.log(Logger.Level.INFO, "In Hotel stay " + countGuestInHotel + " guests, at this moment.");
    }
}
