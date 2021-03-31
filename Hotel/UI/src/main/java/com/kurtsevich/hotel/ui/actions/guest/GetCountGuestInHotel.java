package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetCountGuestInHotel extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(GetCountGuestInHotel.class);

    public GetCountGuestInHotel(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        Integer countGuestInHotel = facade.getCountGuestInHotel();
        logger.info("In Hotel stay " + countGuestInHotel + " guests, at this moment.");
    }
}
