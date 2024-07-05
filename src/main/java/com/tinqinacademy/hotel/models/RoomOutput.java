package com.tinqinacademy.hotel.models;

import com.tinqinacademy.hotel.models.constants.BathroomType;
import com.tinqinacademy.hotel.models.constants.BedSize;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoomOutput {
    private String id;
    private String roomNumber;
    private Integer bedCount;
    private BedSize bedSize;
    private Integer floor;
    private BigDecimal price;
    private BathroomType bathroomType;
}
