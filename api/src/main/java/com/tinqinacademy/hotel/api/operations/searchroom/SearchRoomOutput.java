package com.tinqinacademy.hotel.api.operations.searchroom;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor
public class SearchRoomOutput {
    private List<String> roomIds;
}
