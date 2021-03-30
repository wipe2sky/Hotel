package com.kurtsevich.hotel.server.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest extends AEntity {
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private Room room;
    @Getter
    @Setter
    private History lastHistory;
    @Getter
    @Setter
    private boolean isCheckIn;
    @Getter
    @Setter
    private List<Service> services = new ArrayList<>();
    @Getter
    @Setter
    private List<History> histories = new ArrayList<>();


    public Guest(String lastName, String firstName) {
        setLastName(lastName);
        setFirstName(firstName);

    }
//    public Guest(Integer id, String lastName, String firstName) {
//        setId(id);
//        setLastName(lastName);
//        setFirstName(firstName);
//    }


    @Override
    public String toString() {
        return "Guest{" +
                "id = " + getId() +
                ", lastName = '" + lastName + '\'' +
                ", firstName = '" + firstName + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(firstName, guest.firstName) && Objects.equals(lastName, guest.lastName) && Objects.equals(getId(), guest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, getId());
    }
}
