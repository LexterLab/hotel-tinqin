package com.tinqinacademy.hotel.api.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import io.vavr.control.Either;

public interface OperationProcessor <I extends OperationInput, O extends OperationOutput> {
    Either<ErrorOutput, O> process(I input);
}
