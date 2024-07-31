package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.signup.SignUp;
import com.tinqinacademy.hotel.api.operations.signup.SignUpInput;
import com.tinqinacademy.hotel.api.operations.signup.SignUpOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication REST APIs")
public class AuthenticationController extends BaseController {
    private final SignUp signUp;

    @Operation(
            summary = "Sign Up User Rest API",
            description = "Sign Up User Rest API is used for signing up a user"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "HTTP STATUS 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP STATUS 400 BAD REQUEST"),
            @ApiResponse(responseCode = "403", description = "HTTP STATUS 403 FORBIDDEN"),
    })
    @PostMapping(RestAPIRoutes.SIGN_UP)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpInput input) {
        Either<ErrorOutput, SignUpOutput> output = signUp.process(input);
        return handleOutput(output, HttpStatus.CREATED);
    }
}
