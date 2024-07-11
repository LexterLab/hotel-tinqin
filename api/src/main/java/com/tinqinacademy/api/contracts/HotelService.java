package com.tinqinacademy.api.contracts;


import com.tinqinacademy.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.api.operations.unbookroom.UnbookRoomOutput;

public interface HotelService {

    SearchRoomOutput searchRoom(SearchRoomInput input);
    GetRoomOutput getRoom(GetRoomInput input);
    BookRoomOutput bookRoom(BookRoomInput input);
    UnbookRoomOutput unbookRoom(UnbookRoomInput input);
}
