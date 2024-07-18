package com.tinqinacademy.hotel.api.operations.errors;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ErrorOutput {
    private List<Error> errors;
}
