package com.tinqinacademy.hotel.persistence.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum BedSize {
    SINGLE("single", 2),
    DOUBLE("double", 4),
    SMALL_SINGLE("smallSingle", 1),
    SMALL_DOUBLE("smallDouble", 2),
    KING_SIZE("kingSize", 15),
    UNKNOWN("", 0);


    private final String code;
    @Getter
    private final Integer capacity;



    @JsonCreator
    public static BedSize getByCode(String code) {
        return Arrays.stream(BedSize.values())
                .filter(size -> size.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    @JsonValue
    public String toString() {
        return this.code;
    }
}
