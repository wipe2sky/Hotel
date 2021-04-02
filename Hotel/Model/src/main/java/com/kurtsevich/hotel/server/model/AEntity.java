package com.kurtsevich.hotel.server.model;

import lombok.Getter;
import lombok.Setter;

public abstract class AEntity {
    @Getter
    @Setter
    private Integer id;

    @Override
    public String toString() {
        return "id=" + id;
    }
}
