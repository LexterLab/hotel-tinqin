package com.tinqinacademy.hotel.api.operations.partialupdateroom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tinqinacademy.hotel.api.base.OperationProcessor;

public interface PartialUpdateRoomProcessor extends OperationProcessor<PartialUpdateRoomInput, PartialUpdateRoomOutput> {
    PartialUpdateRoomOutput process(PartialUpdateRoomInput input) throws JsonPatchException, JsonProcessingException
            ;
}
