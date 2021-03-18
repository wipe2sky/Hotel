package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.util.Logger;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetNumberOfFree extends AbstractAction implements IAction {
    private static final Logger logger = new Logger(GetNumberOfFree.class.getName());

    public GetNumberOfFree(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        logger.log(Logger.Level.INFO, "In hotel has " + facade.getNumberOfFree() + " free room at the moment");
    }
}
