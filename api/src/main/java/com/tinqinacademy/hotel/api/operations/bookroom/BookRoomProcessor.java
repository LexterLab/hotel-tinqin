package com.tinqinacademy.hotel.api.operations.bookroom;

import com.tinqinacademy.hotel.api.base.OperationProcessor;

public interface BookRoomProcessor extends OperationProcessor<BookRoomInput, BookRoomOutput> {
    BookRoomOutput process(BookRoomInput input);
}
