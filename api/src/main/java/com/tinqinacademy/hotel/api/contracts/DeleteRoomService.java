package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;

public interface DeleteRoomService {
    DeleteRoomOutput deleteRoom(DeleteRoomInput input);
}
