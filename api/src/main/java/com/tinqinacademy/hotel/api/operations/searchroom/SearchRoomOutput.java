package com.tinqinacademy.hotel.api.operations.searchroom;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor
public class SearchRoomOutput {
    private List<UUID> roomIds;
}
