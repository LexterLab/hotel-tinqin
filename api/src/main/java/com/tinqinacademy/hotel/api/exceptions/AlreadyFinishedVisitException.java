package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.Messages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyFinishedVisitException extends RuntimeException {
    private final String message;

    public AlreadyFinishedVisitException() {
        message = Messages.VISIT_ALREADY_FINISHED;
    }
}
