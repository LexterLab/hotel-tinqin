package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterGuestInput implements OperationInput {
   @JsonIgnore
   @NotBlank(message = "Field bookingId must not be blank")
   @UUID(message = "Field bookingId must be UUID")
   private String bookingId;
   @NotNull(message = "Field guests cannot be null")
   private List<@Valid GuestInput> guests;
}
