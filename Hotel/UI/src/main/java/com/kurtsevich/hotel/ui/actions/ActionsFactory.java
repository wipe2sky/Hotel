package com.kurtsevich.hotel.ui.actions;

import com.kurtsevich.hotel.di.annotation.InjectByType;
import com.kurtsevich.hotel.di.annotation.Singleton;
import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

@Singleton
public class ActionsFactory {
    private final HotelFacade facade;

    @InjectByType
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
    public IAction getAction(Class<? extends IAction> clazz, ComparatorStatus comparatorStatus) {
        return clazz.getConstructor(HotelFacade.class, ComparatorStatus.class).newInstance(facade, comparatorStatus);
    }

    @SneakyThrows
    public IAction getAction(Class<? extends IAction> clazz, ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        return clazz.getConstructor(HotelFacade.class, ComparatorStatus.class, RoomStatus.class).newInstance(facade, comparatorStatus, roomStatus);
    }
}
