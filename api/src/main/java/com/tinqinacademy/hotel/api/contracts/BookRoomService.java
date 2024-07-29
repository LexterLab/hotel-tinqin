package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;

public interface BookRoomService {
    BookRoomOutput bookRoom(BookRoomInput input);
}
