package com.tinqinacademy.hotel.operations.searchroom;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SearchRoomInput {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer bedCount;
    private String bedSize;
    private String bathroomType;
}
