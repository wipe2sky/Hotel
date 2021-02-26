package com.hotel.ui.actions.room;

import com.hotel.model.RoomStatus;
import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;
import com.hotel.util.comparators.ComparatorStatus;

public class GetSortRoomBy extends AbstractAction implements IAction {
    private ComparatorStatus comparatorStatus;
    private RoomStatus roomStatus;

    public GetSortRoomBy(ComparatorStatus comparatorStatus, RoomStatus roomStatus) {
        this.comparatorStatus = comparatorStatus;
        this.roomStatus = roomStatus;
    }

    @Override
    public void execute() {
        facade.getRoomSortBy(comparatorStatus, roomStatus).forEach(System.out::println);
    }
}
