package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;

public interface SearchRoomService {
    SearchRoomOutput searchRoom(SearchRoomInput input);
}
