package com.tinqinacademy.hotel.api.operations.createroom;

import com.tinqinacademy.hotel.api.base.OperationProcessor;

public interface CreateRoomProcessor extends OperationProcessor<CreateRoomInput, CreateRoomOutput> {
   CreateRoomOutput process(CreateRoomInput input);
}
