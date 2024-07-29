package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;

public interface GetRoomService {
    GetRoomOutput getRoom(GetRoomInput input);
}
