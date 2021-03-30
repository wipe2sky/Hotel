package com.kurtsevich.hotel.server.db_model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
public abstract class AEntityDB {
    @Getter
    @Setter
    private Integer id;

    @Override
    public String toString() {
        return "id=" + id;
    }
}
