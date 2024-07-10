package com.tinqinacademy.api.operations.partialupdateroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class PartialUpdateRoomOutput {
    @Schema(example = "UUUID")
    private String roomId;
}
