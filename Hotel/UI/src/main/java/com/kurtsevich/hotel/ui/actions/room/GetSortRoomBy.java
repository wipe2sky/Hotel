package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.controller.HotelFacade;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.SortStatus;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetSortRoomBy extends AbstractAction implements IAction {
    private SortStatus sortStatus;
    private RoomStatus roomStatus;


    public GetSortRoomBy(HotelFacade facade, SortStatus sortStatus, RoomStatus roomStatus) {
        this.facade = facade;
        this.sortStatus = sortStatus;
        this.roomStatus = roomStatus;
    }

    @Override
    public void execute() {
        facade.getRoomSortBy(sortStatus, roomStatus).forEach(System.out::println);
    }
}
