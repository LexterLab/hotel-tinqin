package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.operations.guest.GuestInput;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterGuestInput implements OperationInput {
   @JsonIgnore
   private UUID bookingId;
   @Valid
   List<GuestInput> guests;
}
