package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.exceptions.InputValidationException;
import com.tinqinacademy.hotel.api.errors.Error;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import io.vavr.API;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
public abstract class BaseProcessor {
    protected final ConversionService conversionService;
    protected final Validator validator;

    protected API.Match.Case<Exception, ErrorOutput> customCase(Throwable throwable, HttpStatus status,
                                                                Class<? extends Exception> e) {
        return Case($(instanceOf(e)), () -> ErrorOutput.builder()
                .errors(List.of(Error.builder()
                        .message(throwable.getMessage())
                        .build()))
                .statusCode(status).build());
    }

    protected API.Match.Case<Exception, ErrorOutput> defaultCase(Throwable throwable) {
        return Case($(),() -> ErrorOutput.builder()
                .errors(List.of(Error.builder()
                        .message(throwable.getMessage())
                        .build()))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
    }

    protected API.Match.Case<Exception, ErrorOutput> validatorCase(Throwable throwable) {
        List<Error> errors = mapExceptionToErrors(throwable);
        return Case($(instanceOf(InputValidationException.class)), () -> ErrorOutput.builder()
                .errors(errors)
                .statusCode(HttpStatus.BAD_REQUEST)
                .build());
    }

    protected void validateInput(OperationInput input) {
        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new InputValidationException(mapConstraintViolations(violations));
        }
    }

    private List<Error> mapConstraintViolations(Set<ConstraintViolation<OperationInput>> violations) {
        return  violations.stream()
                .map(violation -> Error.builder()
                        .message(violation.getMessage())
                        .field(violation.getPropertyPath().toString())
                        .build())
                .collect(Collectors.toList());
    }

    private List<Error> mapExceptionToErrors(Throwable throwable) {
        List<Error> errors = new ArrayList<>();
        if (throwable instanceof InputValidationException) {
            ((InputValidationException) throwable).getErrors()
                    .forEach(error -> errors.add(Error.builder()
                            .message(error.getMessage())
                            .field(error.getField()).build()));
        }
        return errors;
    }

}
