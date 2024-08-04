package com.tinqinacademy.hotel.api.operations.signup;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignUpOutput implements OperationOutput {
    private UUID id;
}
