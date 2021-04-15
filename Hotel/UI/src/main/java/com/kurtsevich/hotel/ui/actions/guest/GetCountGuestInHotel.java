package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GetCountGuestInHotel extends AbstractAction implements IAction {

    public GetCountGuestInHotel(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        Long countGuestInHotel = facade.getCountGuestInHotel();
        log.info("In Hotel stay {} guests, at this moment.", countGuestInHotel);
    }
}
