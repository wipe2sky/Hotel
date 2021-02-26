package com.hotel.ui.actions.guest;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.comparators.ComparatorStatus;

public class GetGuestSortBy extends AbstractAction implements IAction {
    private ComparatorStatus comparatorStatus;

    public GetGuestSortBy(ComparatorStatus comparatorStatus) {
        this.comparatorStatus = comparatorStatus;
    }

    @Override
    public void execute() {
        facade.getGuestSortBy(comparatorStatus).forEach(System.out::println);
    }
}
