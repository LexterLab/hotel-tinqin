package com.tinqinacademy.hotel.api.operations.signup;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignUpOutput {
    private UUID id;
}
