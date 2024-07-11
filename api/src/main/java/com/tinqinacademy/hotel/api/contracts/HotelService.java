package com.tinqinacademy.hotel.api.contracts;


import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;

public interface HotelService {

    SearchRoomOutput searchRoom(SearchRoomInput input);
    GetRoomOutput getRoom(GetRoomInput input);
    BookRoomOutput bookRoom(BookRoomInput input);
    UnbookRoomOutput unbookRoom(UnbookRoomInput input);
}
