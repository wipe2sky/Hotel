package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetAllGuest extends AbstractAction implements IAction {

    public GetAllGuest(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.getAllGuest().forEach(System.out::println);
    }
}
