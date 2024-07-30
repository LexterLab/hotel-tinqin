package com.tinqinacademy.hotel.api.operations.getroom;

import com.tinqinacademy.hotel.api.base.OperationProcessor;

public interface GetRoomProcessor extends OperationProcessor<GetRoomInput, GetRoomOutput> {
    GetRoomOutput process(GetRoomInput input);
}
