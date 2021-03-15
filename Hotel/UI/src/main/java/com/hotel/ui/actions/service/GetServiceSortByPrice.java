package com.hotel.ui.actions.service;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
@Singleton
public class GetServiceSortByPrice implements IAction {
    @InjectByType
    private HotelFacade facade;
    @Override
    public void execute() {
        facade.getServiceSortByPrice().forEach(System.out::println);
    }
}
