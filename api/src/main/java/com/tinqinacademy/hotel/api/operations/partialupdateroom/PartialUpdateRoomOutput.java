package com.tinqinacademy.hotel.api.operations.partialupdateroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class PartialUpdateRoomOutput implements OperationOutput {
    @Schema(example = "UUUID")
    private UUID roomId;
}
