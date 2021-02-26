package com.hotel.ui.actions.guest;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetAllGuestInHotel extends AbstractAction implements IAction {
    @Override
    public void execute() {
        facade.getAllGuestInHotel().forEach(System.out::println);
    }
}
