package com.hotel.ui.actions.room;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.Logger;

public class GetNumberOfFree extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetNumberOfFree.class.getName());

    @Override
    public void execute() {
        logger.log(Logger.Level.INFO, "In hotel has " + facade.getNumberOfFree() + " free room at the moment");
    }
}
