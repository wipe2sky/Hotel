package com.kurtsevich.hotel.ui.actions;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.SortStatus;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

@Component
public class ActionsFactory {
    private final HotelFacade facade;

    @Autowired
    public ActionsFactory(HotelFacade facade) {
        this.facade = facade;
    }


    @SneakyThrows
    public IAction getAction(Class<? extends IAction> clazz) {
        return clazz.getConstructor(HotelFacade.class).newInstance(facade);
    }

    @SneakyThrows
    public IAction getAction(Class<? extends IAction> clazz, Boolean status) {
        Constructor<? extends IAction> constructor = clazz.getConstructor(HotelFacade.class, Boolean.class);
        return constructor.newInstance(facade, status);
    }

    @SneakyThrows
    public IAction getAction(Class<? extends IAction> clazz, SortStatus sortStatus) {
        return clazz.getConstructor(HotelFacade.class, SortStatus.class).newInstance(facade, sortStatus);
    }

    @SneakyThrows
    public IAction getAction(Class<? extends IAction> clazz, SortStatus sortStatus, RoomStatus roomStatus) {
        return clazz.getConstructor(HotelFacade.class, SortStatus.class, RoomStatus.class).newInstance(facade, sortStatus, roomStatus);
    }
}
