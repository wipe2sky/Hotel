package com.hotel.ui.actions.room;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetNumberOfFree extends AbstractAction implements IAction {
    @Override
    public void execute() {
        Integer numberOfFree = facade.getNumberOfFree();
        System.out.println();
        System.out.println("На данный момент в гостинице свободно " + numberOfFree + " номеров");
        System.out.println();
    }
}
