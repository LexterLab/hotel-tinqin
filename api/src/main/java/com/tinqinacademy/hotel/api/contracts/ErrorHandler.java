package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorHandler {
    ErrorOutput handle(MethodArgumentNotValidException exception);
}
