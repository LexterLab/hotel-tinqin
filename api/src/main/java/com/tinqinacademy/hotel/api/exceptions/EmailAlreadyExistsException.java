package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.Messages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAlreadyExistsException extends RuntimeException {
    private final String message;

    public EmailAlreadyExistsException() {
        this.message = Messages.EMAIL_ALREADY_EXISTS;
    }
}
