package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.signup.SignUpInput;
import com.tinqinacademy.hotel.api.operations.signup.SignUpOutput;

public interface AuthenticationService {
    SignUpOutput signUp(SignUpInput signUpInput);
}
