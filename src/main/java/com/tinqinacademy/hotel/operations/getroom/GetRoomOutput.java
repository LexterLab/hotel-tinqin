package com.tinqinacademy.hotel.operations.getroom;

import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class GetRoomOutput {
    @Schema(example = "UUID")
    private String id;
    @Schema(example = "3232")
    private BigDecimal price;
    @Schema(example = "4")
    private Integer floor;
    @Schema(example = "single")
    private BedSize bedSize;
    @Schema(example = "shared")
    private BathroomType bathroomType;
    private List<LocalDate> datesOccupied;
}
