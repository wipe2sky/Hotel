package com.hotel.ui.actions.room;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.Singleton;
import com.hotel.server.facade.HotelFacade;
import com.hotel.server.model.RoomStatus;
import com.hotel.server.util.comparators.ComparatorStatus;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
@Singleton
public class GetSortRoomBy implements IAction {
    @InjectByType
    private HotelFacade facade;
    private ComparatorStatus comparatorStatus;
    private RoomStatus roomStatus;

    public GetSortRoomBy() {
    }

    public GetSortRoomBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        this.comparatorStatus = comparatorStatus;
        this.roomStatus = roomStatus;
    }

    @Override
    public void execute() {
        facade.getRoomSortBy(comparatorStatus, roomStatus).forEach(System.out::println);
    }
}
