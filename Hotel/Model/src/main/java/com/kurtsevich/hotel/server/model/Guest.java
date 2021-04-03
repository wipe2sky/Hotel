package com.kurtsevich.hotel.server.model;

import lombok.Data;

@Data
public class Guest extends AEntity {
    private String lastName;
    private String firstName;
    private boolean isCheckIn;


    public Guest(String lastName, String firstName) {
        setLastName(lastName);
        setFirstName(firstName);

    }

    public Guest(Integer id, String lastName, String firstName, boolean isCheckIn) {
        setId(id);
        setLastName(lastName);
        setFirstName(firstName);
        setCheckIn(isCheckIn);
    }

}
