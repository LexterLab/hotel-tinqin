package com.tinqinacademy.hotel.api.operations.getroom;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BedOutput {
    private UUID id;
    private BedSize bedSize;
    private Integer bedCapacity;
}
