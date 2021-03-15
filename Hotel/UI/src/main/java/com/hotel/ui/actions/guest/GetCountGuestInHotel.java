package com.hotel.ui.actions.guest;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.util.Logger;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
@Singleton
public class GetCountGuestInHotel  implements IAction {
    private static final Logger logger = new Logger(GetCountGuestInHotel.class.getName());
    @InjectByType
    private HotelFacade facade;

    @Override
    public void execute() {
       Integer countGuestInHotel = facade.getCountGuestInHotel();
       logger.log(Logger.Level.INFO, "In Hotel stay " + countGuestInHotel + " guests, at this moment.");
    }
}
