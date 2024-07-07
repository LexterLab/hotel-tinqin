package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.operations.unbookroom.UnbookRoomOutput;

public interface HotelService {

    SearchRoomOutput searchRoom(SearchRoomInput input);
    GetRoomOutput getRoom(GetRoomInput input);
    BookRoomOutput bookRoom(BookRoomInput input);
    UnbookRoomOutput unbookRoom(UnbookRoomInput input);
}
