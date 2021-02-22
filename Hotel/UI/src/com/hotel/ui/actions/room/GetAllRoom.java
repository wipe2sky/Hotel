package com.hotel.ui.actions.room;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetAllRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        System.out.println();
        facade.getAllRoom().forEach(System.out::println);
        System.out.println();
    }
}
