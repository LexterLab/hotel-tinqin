package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;

public interface ErrorHandler {
    ErrorOutput handle(Exception exception);
}
