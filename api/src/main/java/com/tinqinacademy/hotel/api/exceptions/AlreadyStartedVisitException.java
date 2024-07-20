package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.Messages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyStartedVisitException extends RuntimeException {
    private final String message;

    public AlreadyStartedVisitException() {
        message = Messages.VISIT_ALREADY_STARTED;
    }
}
