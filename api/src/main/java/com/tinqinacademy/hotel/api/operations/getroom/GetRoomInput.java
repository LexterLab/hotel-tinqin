package com.tinqinacademy.hotel.api.operations.getroom;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class GetRoomInput {
    private UUID roomId;
}
