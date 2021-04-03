package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetAllRoom extends AbstractAction implements IAction {

    public GetAllRoom(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.getAllRoom().forEach(System.out::println);
    }
}
