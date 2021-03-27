package com.kurtsevich.hotel.ui.actions;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.util.IdGenerator;
import com.kurtsevich.hotel.server.util.SerializationHandler;

@Singleton
public class SaveProgram implements IAction {
    private final IdGenerator idGenerator;
    private final HotelFacade facade;
    private final SerializationHandler serializationHandler;

    @InjectByType
    public SaveProgram(IdGenerator idGenerator, HotelFacade facade, SerializationHandler serializationHandler) {
        this.idGenerator = idGenerator;
        this.facade = facade;
        this.serializationHandler = serializationHandler;
    }

    @Override
    public void execute() {
        serializationHandler.serialize(facade.getAllGuest(), facade.getAllHistories(), facade.getAllRoom(), facade.getAllService());
        serializationHandler.serialize(idGenerator);
    }
}
