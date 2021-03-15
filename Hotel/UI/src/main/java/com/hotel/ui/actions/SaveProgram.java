package com.hotel.ui.actions;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.util.IdGenerator;
import com.hotel.server.util.SerializationHandler;

@Singleton
public class SaveProgram  implements IAction {
    @InjectByType
    private IdGenerator idGenerator;
    @InjectByType
    private HotelFacade facade;
    @InjectByType
    SerializationHandler serializationHandler;

    @Override
    public void execute() {
        serializationHandler.serialize(facade.getAllGuest(), facade.getAllHistories(), facade.getAllRoom(), facade.getAllService());
        serializationHandler.serialize(idGenerator);
    }
}
