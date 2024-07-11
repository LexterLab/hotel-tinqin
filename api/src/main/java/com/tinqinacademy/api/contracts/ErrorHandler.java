package com.tinqinacademy.api.contracts;

import com.tinqinacademy.api.operations.errors.ErrorOutput;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorHandler {
    ErrorOutput handle(MethodArgumentNotValidException exception);
}
