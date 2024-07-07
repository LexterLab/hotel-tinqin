package com.tinqinacademy.hotel.operations.updateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UpdateRoomInput {
    @JsonIgnore
    private String roomId;
    @Schema(example = "2")
    private Integer bedCount;
    @Schema(example = "single")
    private BedSize bedSize;
    @Schema(example = "private")
    private BathroomType bathroomType;
    @Schema(example = "4")
    private Integer floor;
    @Schema(example = "201A")
    private String roomNo;
    @Schema(example = "2000")
    private BigDecimal price;
}
