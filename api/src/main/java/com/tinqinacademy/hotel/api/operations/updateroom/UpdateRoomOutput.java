package com.tinqinacademy.hotel.api.operations.updateroom;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UpdateRoomOutput {
    private UUID roomId;
}
