package com.tinqinacademy.api.models.constants;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

public enum BedSize {
    SINGLE("single"),
    DOUBLE("double"),
    SMALL_SINGLE("smallSingle"),
    SMALL_DOUBLE("smallDouble"),
    KING_SIZE("kingSize"),
    UNKNOWN("");


    private final String code;

    BedSize(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BedSize getByCode(String code) {
        return Arrays.stream(BedSize.values())
                .filter(size -> size.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }

    @Override
    @JsonValue
    public String toString() {
        return this.code;
    }
}
