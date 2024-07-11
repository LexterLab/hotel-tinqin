package com.tinqinacademy.hotel.api.models.errors;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Error {
    private String message;
    private String field;
}
