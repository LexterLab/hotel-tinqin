package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestOutput;

public interface RegisterGuestService {
    RegisterGuestOutput registerGuest(RegisterGuestInput input);
}
