package com.tinqinacademy.hotel.api.operations.createroom;


import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateRoomInput(
        @Schema(example = "2")
        @Min(value = 1, message = "Field bedCount must be minimum 1")
        @Max(value = 10, message = "Field bedCount must be maximum 10")
        Integer bedCount,
        @Schema(example = "single")
        @NotNull(message = "Field bedSize must not be null")
        BedSize bedSize,
        @Schema(example = "private")
        @NotNull(message = "Field bathroomType must not be null")
        BathroomType bathroomType,
        @Schema(example = "4")
        @Min(value = 1, message = "Field floor must be minimum 1")
        @Max(value = 10, message = "Field floor must be maximum 12")
        Integer floor,
        @Schema(example = "201A")
        @NotBlank(message = "Field roomNo must not be empty")
        @Size(min = 3, max = 5, message = "Field roomNo must be between 3-5 characters")
        String roomNo,
        @Schema(example = "20000")
        @PositiveOrZero(message = "Field price must be min 0")
        BigDecimal price
) {}
