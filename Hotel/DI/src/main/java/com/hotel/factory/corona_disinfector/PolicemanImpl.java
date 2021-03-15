package com.hotel.factory.corona_disinfector;

import com.hotel.factory.annotation.InjectByType;
import com.hotel.factory.annotation.PostConstruct;

public class PolicemanImpl implements Policeman {

    @InjectByType
    private Recommendator recommendator;

    @PostConstruct
    public void init() {
        System.out.println(recommendator.getClass());
    }

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Пошли все вон! Пыщ-пыщ! Тра-та-та!");
    }


}
