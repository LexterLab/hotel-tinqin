package com.tinqinacademy.hotel.api.operations.partialupdateroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class PartialUpdateRoomOutput {
    @Schema(example = "UUUID")
    private UUID roomId;
}
