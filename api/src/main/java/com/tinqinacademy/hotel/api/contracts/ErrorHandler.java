package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;

public interface ErrorHandler {
    ErrorOutput handle(Exception exception);
}
