package com.tinqinacademy.hotel.api.operations.updateroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UpdateRoomOutput {
    @Schema(example = "UUID")
    private String roomId;
}
