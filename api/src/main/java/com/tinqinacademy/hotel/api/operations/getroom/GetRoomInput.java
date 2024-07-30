package com.tinqinacademy.hotel.api.operations.getroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class GetRoomInput implements OperationInput {
    private UUID roomId;
}
