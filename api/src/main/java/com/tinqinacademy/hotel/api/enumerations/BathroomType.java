package com.tinqinacademy.hotel.api.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BathroomType {
    PRIVATE("private"),
    SHARED("shared"),
    UNKNOWN("");

    private final String code;

    BathroomType(String code) {
        this.code = code;
    }


    @JsonCreator
    public static BathroomType getByCode(String code) {
        return Arrays.stream(BathroomType.values())
                .filter(bathroomType -> bathroomType.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    @JsonValue
    public String toString() {
        return this.code;
    }
}
