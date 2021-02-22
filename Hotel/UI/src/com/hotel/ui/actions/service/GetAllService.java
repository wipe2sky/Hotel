package com.hotel.ui.actions.service;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetAllService extends AbstractAction implements IAction {
    @Override
    public void execute() {
        System.out.println();
        facade.getAllService().forEach(System.out::println);
        System.out.println();
    }
}
