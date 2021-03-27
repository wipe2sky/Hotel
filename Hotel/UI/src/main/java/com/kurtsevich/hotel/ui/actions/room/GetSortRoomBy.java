package com.kurtsevich.hotel.ui.actions.room;

import com.kurtsevich.hotel.server.facade.HotelFacade;
import com.kurtsevich.hotel.server.model.RoomStatus;
import com.kurtsevich.hotel.server.util.comparators.ComparatorStatus;
import com.kurtsevich.hotel.ui.actions.AbstractAction;
import com.kurtsevich.hotel.ui.actions.IAction;

public class GetSortRoomBy extends AbstractAction implements IAction {
    private ComparatorStatus comparatorStatus;
    private RoomStatus roomStatus;


    public GetSortRoomBy(HotelFacade facade, ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        this.facade = facade;
        this.comparatorStatus = comparatorStatus;
        this.roomStatus = roomStatus;
    }

    @Override
    public void execute() {
        facade.getRoomSortBy(comparatorStatus, roomStatus).forEach(System.out::println);
    }
}
