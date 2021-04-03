package com.kurtsevich.hotel.ui.actions.service;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetAllService extends AbstractAction implements IAction {

    public GetAllService(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.getAllService().forEach(System.out::println);
    }
}
