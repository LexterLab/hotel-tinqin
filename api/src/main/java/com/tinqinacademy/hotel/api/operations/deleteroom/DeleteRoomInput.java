package com.tinqinacademy.hotel.api.operations.deleteroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeleteRoomInput {
    @Schema(example = "UUID")
    private String roomId;
}
