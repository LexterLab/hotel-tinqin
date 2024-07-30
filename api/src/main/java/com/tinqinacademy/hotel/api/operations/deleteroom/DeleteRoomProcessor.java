package com.tinqinacademy.hotel.api.operations.deleteroom;

import com.tinqinacademy.hotel.api.base.OperationProcessor;

public interface DeleteRoomProcessor extends OperationProcessor<DeleteRoomInput, DeleteRoomOutput> {
    DeleteRoomOutput process(DeleteRoomInput input);
}
