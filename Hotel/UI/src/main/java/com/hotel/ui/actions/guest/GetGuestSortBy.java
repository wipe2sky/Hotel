package com.hotel.ui.actions.guest;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.util.comparators.ComparatorStatus;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
@Singleton
public class GetGuestSortBy  implements IAction {
    private ComparatorStatus comparatorStatus;
    @InjectByType
    private HotelFacade facade;
    public GetGuestSortBy() {
    }

    public GetGuestSortBy(ComparatorStatus comparatorStatus) {
        this.comparatorStatus = comparatorStatus;
    }

    @Override
    public void execute() {
        facade.getGuestSortBy(comparatorStatus).forEach(System.out::println);
    }
}
