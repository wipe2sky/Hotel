package com.kurtsevich.hotel.ui.actions.history;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetAllHistories extends AbstractAction implements IAction {

    public GetAllHistories(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.getAllHistories().forEach(System.out::println);
    }
}
