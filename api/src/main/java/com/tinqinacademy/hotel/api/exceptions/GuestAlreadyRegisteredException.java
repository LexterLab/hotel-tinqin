package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.Messages;
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
        this.message = String.format(Messages.GUEST_ALREADY_REGISTERED, guestId, bookingId);
    }
}
