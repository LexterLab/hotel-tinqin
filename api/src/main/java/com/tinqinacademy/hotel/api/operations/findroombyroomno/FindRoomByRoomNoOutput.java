package com.tinqinacademy.hotel.api.operations.findroombyroomno;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindRoomByRoomNoOutput implements OperationOutput {
    private UUID id;
    @Schema(example = "201A")
    private String roomNo;
}
