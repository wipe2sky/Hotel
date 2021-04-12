package com.kurtsevich.hotel.server.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(exclude="histories")
@Entity
@Table(name = "guest")
public class Guest extends AEntity {
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "is_check_in")
    private boolean isCheckIn = false;
    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY)
    private List<History> histories;

    public Guest() {
    }

    public Guest(String lastName, String firstName) {
        setLastName(lastName);
        setFirstName(firstName);

    }
}
