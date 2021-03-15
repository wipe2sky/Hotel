package com.hotel.factory.corona_disinfector;

import com.hotel.factory.annotation.InjectProperty;
import com.hotel.factory.annotation.Singleton;

@Singleton
public class RecommendatorImpl implements Recommendator {
    @InjectProperty("wisky")
    private String alcohol;

    public RecommendatorImpl() {
        System.out.println("Recommendator was created.");
    }


    @Override
    public void recommend() {
        System.out.println("to protected to COVID-19 drink " + alcohol);
    }
}
