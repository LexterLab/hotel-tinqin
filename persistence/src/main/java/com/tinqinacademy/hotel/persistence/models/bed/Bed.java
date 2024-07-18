package com.tinqinacademy.hotel.persistence.models.bed;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Bed {
    private UUID id;
    private BedSize bedSize;
    private Integer bedCapacity;
}
