package com.tinqinacademy.api.operations.errors;

import com.tinqinacademy.api.models.errors.Error;
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
