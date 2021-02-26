package com.hotel.ui.actions.guest;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetAllGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        facade.getAllGuest().forEach(System.out::println);
    }
}
