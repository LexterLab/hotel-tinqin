package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<?> handleOutput(Either<ErrorOutput, ? extends OperationOutput> output, HttpStatus status) {
        return output.fold(
                errorOutput -> new ResponseEntity<>(errorOutput, errorOutput.getStatusCode()),
                operationOutput -> new ResponseEntity<>(operationOutput, status)
        );
    }
}
