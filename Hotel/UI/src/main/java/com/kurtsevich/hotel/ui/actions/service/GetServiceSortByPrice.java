package com.kurtsevich.hotel.ui.actions.service;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetServiceSortByPrice extends AbstractAction implements IAction {

    public GetServiceSortByPrice(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        facade.getServiceSortByPrice().forEach(System.out::println);
    }
}
