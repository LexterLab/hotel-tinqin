package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.Messages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomNoAlreadyExistsException extends RuntimeException {
    private final String message;
    private final String roomNo;

    public RoomNoAlreadyExistsException(String roomNo) {
        this.roomNo = roomNo;
        this.message = String.format(Messages.ROOM_NO_ALREADY_EXISTS, roomNo);
    }
}
