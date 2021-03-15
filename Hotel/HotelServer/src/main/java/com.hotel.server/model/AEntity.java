package com.hotel.server.model;

import java.io.Serializable;

public abstract class AEntity implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
