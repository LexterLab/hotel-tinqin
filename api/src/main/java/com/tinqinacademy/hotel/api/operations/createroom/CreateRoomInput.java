package com.tinqinacademy.hotel.api.operations.createroom;


import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CreateRoomInput (
        @Schema(example = "private")
        @NotNull(message = "Field bathroomType must not be null")
        BathroomType bathroomType,
        @Schema(example = "4")
        @Min(value = 1, message = "Field floor must be minimum 1")
        @Max(value = 10, message = "Field floor must be maximum 12")
        @NotNull(message = "Field floor cannot be null")
        Integer floor,
        @Schema(example = "201A")
        @NotBlank(message = "Field roomNo must not be empty")
        @Size(min = 4, max = 4, message = "Field roomNo must be between 4 characters")
        String roomNo,
        @Schema(example = "20000")
        @PositiveOrZero(message = "Field price must be min 0")
        @NotNull(message = "Field price cannot be null")
        BigDecimal price,
        List<BedSize> beds
) {}
