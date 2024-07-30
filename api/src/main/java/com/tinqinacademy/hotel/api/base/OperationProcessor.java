package com.tinqinacademy.hotel.api.base;

public interface OperationProcessor <I extends OperationInput, O extends OperationOutput> {
    O process(I input);
}
