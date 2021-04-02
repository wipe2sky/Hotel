package com.kurtsevich.hotel.ui.actions.guest;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetGuestSortBy extends AbstractAction implements IAction {
    private ComparatorStatus comparatorStatus;


    public GetGuestSortBy(HotelFacade facade, ComparatorStatus comparatorStatus) {
        this.facade = facade;
        this.comparatorStatus = comparatorStatus;
    }

    @Override
    public void execute() {
        facade.getGuestSortBy(comparatorStatus).forEach(System.out::println);
    }
}
