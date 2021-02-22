package com.hotel.util.comparators;

import com.hotel.model.Service;

import java.util.Comparator;

public class ServiceCostComparator implements Comparator<Service> {

    @Override
    public int compare(Service o1, Service o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}