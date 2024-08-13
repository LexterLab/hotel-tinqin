package com.tinqinacademy.hotel.api.operations.findroombyroomno;

import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindRoomByRoomNoInput implements OperationInput {
    @NotBlank(message = "Field roomNo must not be blank")
    private String roomNo;
}
