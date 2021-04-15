package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GetNumberOfFree extends AbstractAction implements IAction {

    public GetNumberOfFree(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        log.info("In hotel has {} free room at the moment", facade.getNumberOfFree());
    }
}
