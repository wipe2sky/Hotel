package com.hotel.factory.corona_disinfector;

import com.hotel.factory.annotation.InjectByType;

public class CoronaDisinfector {

    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;


    public void start(Room room) {
        announcer.announce("Начинаем дезинфекцию! Идите вон!");
        policeman.makePeopleLeaveRoom();
        disinfect(room);
        announcer.announce("Попробуйте зайти обратно");
    }

    private void disinfect(Room room){
        System.out.println("Раз-два-три! Корона уйди!");
    }
}
