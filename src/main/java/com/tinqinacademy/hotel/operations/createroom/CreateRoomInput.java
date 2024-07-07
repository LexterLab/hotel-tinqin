package com.tinqinacademy.hotel.operations.createroom;

import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;


public record CreateRoomInput(
        @Schema(example = "2")
        Integer bedCount,
        @Schema(example = "single")
        BedSize bedSize,
        @Schema(example = "private")
        BathroomType bathroomType,
        @Schema(example = "4")
        Integer floor,
        @Schema(example = "201A")
        String roomNo,
        @Schema(example = "20000")
        BigDecimal price
) {}
