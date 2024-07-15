package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.tinqinacademy.hotel.api.operations.visitor.VisitorInput;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterVisitorInput {
   @Valid
   List<VisitorInput> visitors;
}
