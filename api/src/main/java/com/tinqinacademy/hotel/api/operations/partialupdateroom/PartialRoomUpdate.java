package com.tinqinacademy.hotel.api.operations.partialupdateroom;

import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PartialRoomUpdate {
    private BathroomType bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
}
