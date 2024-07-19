package com.tinqinacademy.hotel.api.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAlreadyExistsException extends RuntimeException {
    private final String message;

    public EmailAlreadyExistsException() {
        this.message = "Email already exists";
    }
}
