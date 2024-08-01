package com.tinqinacademy.hotel.api.errors;

import lombok.*;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ErrorOutput {
    private List<Error> errors;
    private HttpStatusCode statusCode;
}
