package com.tinqinacademy.hotel.core.patches.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.core.mappers.RoomMapper;
import com.tinqinacademy.hotel.core.patches.PatchOperation;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
@RequiredArgsConstructor
public class RoomPatchOperation implements PatchOperation<Room, PartialUpdateRoomInput> {
    private final ObjectMapper objectMapper;
    private final Validator validator;

    // W.I.P
    @Override
    public Room apply(JsonPatch patch, Room target) throws JsonProcessingException, JsonPatchException {
        JsonNode patched = patch.apply(objectMapper.convertValue(target, JsonNode.class));
        validate(RoomMapper.INSTANCE.roomToPartialUpdateRoom(target));
        return objectMapper.treeToValue(patched, Room.class);
    }

    // W.I.P
    @Override
    public void validate(PartialUpdateRoomInput input) {
        Set<ConstraintViolation<PartialUpdateRoomInput>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
