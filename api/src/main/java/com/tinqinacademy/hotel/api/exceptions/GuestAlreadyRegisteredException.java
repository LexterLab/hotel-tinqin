package com.tinqinacademy.hotel.api.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GuestAlreadyRegisteredException extends RuntimeException {
    private final String guestId;
    private final String bookingId;
    private final String message;

    public GuestAlreadyRegisteredException(UUID guestId, UUID bookingId) {
        this.guestId = guestId.toString();
        this.bookingId = bookingId.toString();
        this.message = String.format("Guest with id %s already registered with booking: %s", guestId, bookingId);
    }
}
