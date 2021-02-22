package com.hotel.ui.actions.room;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetNumberOfFree extends AbstractAction implements IAction {
    @Override
    public void execute() {
        System.out.println();
        System.out.println("На данный момент в гостинице свободно " + facade.getNumberOfFree() + " номеров");
        System.out.println();
    }
}
