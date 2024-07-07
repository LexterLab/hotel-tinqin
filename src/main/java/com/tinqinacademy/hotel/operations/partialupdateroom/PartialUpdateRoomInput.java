package com.tinqinacademy.hotel.operations.partialupdateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
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
    private String roomId;
    private Integer bedCount;
    private BedSize bedSize;
    private BathroomType bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
}
