package com.tinqinacademy.hotel.api.operations.getroom;


import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class GetRoomOutput {
    private UUID id;
    @Schema(example = "3232")
    private BigDecimal price;
    @Schema(example = "4")
    private Integer floor;
    private List<BedOutput> beds;
    private BathroomType bathroomType;
    private List<LocalDateTime> datesOccupied;
}
