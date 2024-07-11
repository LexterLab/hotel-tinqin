package com.tinqinacademy.hotel.api.operations.partialupdateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class PartialUpdateRoomInput {
    @JsonIgnore
    private String roomId;@Schema(example = "2")
    @Min(value = 1, message = "Field bedCount must be minimum 1")
    @Max(value = 10, message = "Field bedCount must be maximum 10")
    private Integer bedCount;
    @Schema(example = "single")
    @NotNull(message = "Field bedSize must not be null")
    private BedSize bedSize;
    @Schema(example = "private")
    @NotNull(message = "Field bathroomType must not be null")
    private BathroomType bathroomType;
    @Schema(example = "4")
    @Min(value = 1, message = "Field floor must be minimum 1")
    @Max(value = 10, message = "Field floor must be maximum 12")
    private Integer floor;
    @Schema(example = "201A")
    @NotBlank(message = "Field roomNo must not be empty")
    @Size(min = 3, max = 5, message = "Field roomNo must be between 3-5 characters")
    private String roomNo;
    @Schema(example = "20000")
    @PositiveOrZero(message = "Field price must be min 0")
    private BigDecimal price;
}
