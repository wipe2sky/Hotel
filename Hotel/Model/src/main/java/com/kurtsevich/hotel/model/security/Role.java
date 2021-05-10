package com.kurtsevich.hotel.model.security;

import com.kurtsevich.hotel.model.AEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@ToString(exclude="users")
public class Role extends AEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
