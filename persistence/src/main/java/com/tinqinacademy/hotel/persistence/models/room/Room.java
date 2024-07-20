package com.tinqinacademy.hotel.persistence.models.room;

import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Room {
    private UUID id;
    private String roomNo;
    private BathroomType bathroomType;
    private Integer floor;
    private BigDecimal price;
}
