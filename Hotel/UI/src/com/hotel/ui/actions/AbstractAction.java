package com.hotel.ui.actions;

import com.hotel.facade.HotelFacade;

import java.util.Properties;

public class AbstractAction {
    protected HotelFacade facade = HotelFacade.getInstance();
}
