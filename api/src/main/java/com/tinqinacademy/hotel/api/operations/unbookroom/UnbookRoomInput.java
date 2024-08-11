package com.tinqinacademy.hotel.api.operations.unbookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UnbookRoomInput implements OperationInput {
    @JsonIgnore
    @UUID(message = "Field roomId must be UUID")
    @NotBlank(message = "Field roomId must not be blank")
    private String bookingId;
    @UUID(message = "Field userId must be UUID")
    @NotBlank(message = "Field userId must not be blank")
    private String userId;
}
