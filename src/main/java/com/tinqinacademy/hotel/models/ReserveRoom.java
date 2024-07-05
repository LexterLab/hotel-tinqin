package com.tinqinacademy.hotel.models;

import com.tinqinacademy.hotel.models.constants.BedSize;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ReserveRoom {
    private Integer floor;
    private String bedSize;
    private String id;
}
