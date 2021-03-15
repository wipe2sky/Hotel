package com.hotel.ui.actions;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import lombok.SneakyThrows;

@Singleton
public class ActionsFactory {
    private HotelFacade facade;

@InjectByType
    public ActionsFactory(HotelFacade facade) {
        this.facade = facade;
    }

    @SneakyThrows
    public IAction getAction(Class<? extends IAction> clazz)  {

    return clazz.getDeclaredConstructor().newInstance();
    }
}
