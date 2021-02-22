package com.hotel.ui.actions.history;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetAllHistories extends AbstractAction implements IAction {
    @Override
    public void execute() {
        System.out.println();
        facade.getAllHistories().forEach(System.out::println);
        System.out.println();
    }
}
