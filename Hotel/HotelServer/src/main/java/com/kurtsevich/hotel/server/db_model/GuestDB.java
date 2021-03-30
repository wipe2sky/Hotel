package com.kurtsevich.hotel.server.db_model;

import lombok.Data;

@Data
public class GuestDB extends AEntityDB {
    private String lastName;
    private String firstName;
    private boolean isCheckIn;


    public GuestDB(String lastName, String firstName) {
        setLastName(lastName);
        setFirstName(firstName);

    }

    public GuestDB(Integer id, String lastName, String firstName, boolean isCheckIn) {
        setId(id);
        setLastName(lastName);
        setFirstName(firstName);
        setCheckIn(isCheckIn);
    }

}
