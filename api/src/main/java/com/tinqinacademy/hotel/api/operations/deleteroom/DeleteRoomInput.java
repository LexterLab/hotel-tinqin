package com.tinqinacademy.hotel.api.operations.deleteroom;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeleteRoomInput {
    private UUID roomId;
}
