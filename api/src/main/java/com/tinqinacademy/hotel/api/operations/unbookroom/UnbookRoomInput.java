package com.tinqinacademy.hotel.api.operations.unbookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UnbookRoomInput implements OperationInput {
    @JsonIgnore
    private UUID roomId;
    private UUID userId;
}
