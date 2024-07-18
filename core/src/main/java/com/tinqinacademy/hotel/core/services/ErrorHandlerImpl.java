package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.ErrorHandler;
import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorHandlerImpl implements ErrorHandler {
    @Override
    public ErrorOutput handle(Exception exception) {
        List<Error> errors = new ArrayList<>();

       if (exception instanceof MethodArgumentNotValidException ex) {
           errors = handleMethodArgumentNotValid(ex);
       } else {
           Error error = Error.builder().message(exception.getMessage()).build();
           errors.add(error);
       }

        ErrorOutput errorOutput = ErrorOutput.builder()
                .errors(errors)
                .build();

        return errorOutput;
    }

    private List<Error> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<Error> errors = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.add(Error.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField()).build()));

        return errors;
    }
}
