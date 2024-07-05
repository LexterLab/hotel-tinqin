package com.tinqinacademy.hotel.models.input;

import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;

import java.math.BigDecimal;


public record CreateRoomInput(
        Integer bedCount,
        BedSize bedSize,
        BathroomType bathroomType,
        Integer floor,
        String roomNo,
        BigDecimal price
) {}
