package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetAllGuestInHotel extends AbstractAction implements IAction {

    public GetAllGuestInHotel(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.getAllGuestInHotel().forEach(System.out::println);
    }
}
