package com.tinqinacademy.hotel.models.input;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class GetRoomInput {
    private String roomId;
}
