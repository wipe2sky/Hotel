package com.kurtsevich.hotel.server.util;

import lombok.Getter;

public enum SortStatus {
    DATE_CHECK_OUT("check_out_date"),
    LAST_NAME("last_name"),
    CAPACITY("capacity"),
    PRICE("price"),
    STARS("stars");

    @Getter
    private String value;

    SortStatus(String value) {
        this.value = value;
    }
}

