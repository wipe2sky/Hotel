package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.util.SortStatus;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetGuestSortBy extends AbstractAction implements IAction {
    private SortStatus sortStatus;


    public GetGuestSortBy(HotelFacade facade, SortStatus sortStatus) {
        this.facade = facade;
        this.sortStatus = sortStatus;
    }

    @Override
    public void execute() {
        facade.getGuestSortBy(sortStatus).forEach(System.out::println);
    }
}
