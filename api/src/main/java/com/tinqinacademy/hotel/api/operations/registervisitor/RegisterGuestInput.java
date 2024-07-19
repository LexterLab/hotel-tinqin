package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.tinqinacademy.hotel.api.operations.visitor.GuestInput;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterGuestInput {
   @Valid
   List<GuestInput> guests;
}
