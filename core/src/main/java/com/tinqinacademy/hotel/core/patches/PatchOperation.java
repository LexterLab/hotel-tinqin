package com.tinqinacademy.hotel.core.patches;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

public interface PatchOperation<T, J> {

    T apply(JsonPatch patch, T target) throws JsonProcessingException, JsonPatchException;
    void validate(J input);
}
