package com.hotel.ui.actions.guest;

import com.hotel.ui.actions.AbstractAction;
import com.hotel.ui.actions.IAction;

public class GetCountGuestInHotel extends AbstractAction implements IAction {
    @Override
    public void execute() {
       Integer countGuestInHotel = facade.getCountGuestInHotel();
        System.out.println();
        System.out.println("На данный момент в отеле проживает " +countGuestInHotel+" гостей");
        System.out.println();


    }
}
