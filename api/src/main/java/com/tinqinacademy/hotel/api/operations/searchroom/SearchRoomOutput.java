package com.tinqinacademy.hotel.api.operations.searchroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor
public class SearchRoomOutput implements OperationOutput {
    private List<UUID> roomIds;
}
