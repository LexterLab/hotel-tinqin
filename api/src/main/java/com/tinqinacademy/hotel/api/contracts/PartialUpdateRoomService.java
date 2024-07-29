package com.tinqinacademy.hotel.api.contracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;

public interface PartialUpdateRoomService {
    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input)
            throws JsonPatchException, JsonProcessingException;
}
