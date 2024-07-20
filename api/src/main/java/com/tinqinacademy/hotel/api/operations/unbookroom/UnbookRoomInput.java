package com.tinqinacademy.hotel.api.operations.unbookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UnbookRoomInput {
    @JsonIgnore
    private UUID roomId;
    private UUID userId;
}
