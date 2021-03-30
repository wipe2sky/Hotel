package com.kurtsevich.hotel.server.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public abstract class AEntity implements Serializable {
    @Getter
    @Setter
    private Integer id;
}
