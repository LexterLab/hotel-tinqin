package com.tinqinacademy.hotel.api.exceptions;


import com.tinqinacademy.hotel.api.Messages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDateNotAvailableException extends RuntimeException {
    private final String message;

    public BookingDateNotAvailableException() {
        this.message = Messages.DATE_NOT_AVAILABLE;
    }
}
