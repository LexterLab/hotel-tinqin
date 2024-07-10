package com.tinqinacademy.api.operations.createroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateRoomOutput {
    @Schema(example = "UUID")
    String roomId;
}
