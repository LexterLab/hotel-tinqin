package com.tinqinacademy.core.services.impl;

import com.tinqinacademy.api.contracts.ErrorHandler;
import com.tinqinacademy.api.models.errors.Error;
import com.tinqinacademy.api.operations.errors.ErrorOutput;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorHandlerImpl implements ErrorHandler {
    @Override
    public ErrorOutput handle(MethodArgumentNotValidException exception) {
        List<Error> errors = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.add(Error.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField()).build()));

        ErrorOutput errorOutput = ErrorOutput.builder()
                .errors(errors)
                .build();

        return errorOutput;
    }
}
