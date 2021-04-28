package com.kurtsevich.hotel.server;

import lombok.Getter;

public enum SortStatus {
    DATE_CHECK_OUT("checkOutDate"),
    LAST_NAME("lastName"),
    CAPACITY("capacity"),
    PRICE("price"),
    STARS("stars");

    @Getter
    private String value;

    SortStatus(String value) {
        this.value = value;
    }
}

