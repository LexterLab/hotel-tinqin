package com.tinqinacademy.hotel.api.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;

public interface OperationProcessor <I extends OperationInput, O extends OperationOutput> {
    O process(I input) throws JsonPatchException, JsonProcessingException;
}
