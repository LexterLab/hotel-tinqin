package com.tinqinacademy.hotel.api.operations.searchroom;

import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SearchRoomInput {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer bedCount;
    private BedSize bedSize;
    private BathroomType bathroomType;
}
