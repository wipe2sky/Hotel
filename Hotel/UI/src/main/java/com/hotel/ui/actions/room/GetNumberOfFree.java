package com.hotel.ui.actions.room;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
@Singleton
public class GetNumberOfFree  implements IAction {
    private static final Logger logger = new Logger(GetNumberOfFree.class.getName());
    @InjectByType
    private HotelFacade facade;

    @Override
    public void execute() {
        logger.log(Logger.Level.INFO, "In hotel has " + facade.getNumberOfFree() + " free room at the moment");
    }
}
