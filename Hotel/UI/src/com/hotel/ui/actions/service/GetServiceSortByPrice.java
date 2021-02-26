package com.hotel.ui.actions.service;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetServiceSortByPrice extends AbstractAction implements IAction {
    @Override
    public void execute() {
        facade.getServiceSortByPrice().forEach(System.out::println);
    }
}
