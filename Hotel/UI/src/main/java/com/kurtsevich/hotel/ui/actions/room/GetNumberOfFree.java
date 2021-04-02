package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetNumberOfFree extends AbstractAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(GetNumberOfFree.class);

    public GetNumberOfFree(HotelFacade facade) {
        this.facade = facade;
    }

    @Override
    public void execute() {
        logger.info("In hotel has {} free room at the moment", facade.getNumberOfFree());
    }
}
