package com.tinqinacademy.hotel.api.operations.createroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateRoomOutput implements OperationOutput {
    @Schema(example = "UUID")
    String roomId;
}
