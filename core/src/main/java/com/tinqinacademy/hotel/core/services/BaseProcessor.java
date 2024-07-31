package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.exceptions.InputValidationException;
import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import io.vavr.API;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;

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

    protected API.Match.Case<Exception, ErrorOutput> validatorCase(Throwable throwable,
                                                                   Class<InputValidationException> e) {
        List<Error> errors = new ArrayList<>();
        ((InputValidationException) throwable).getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.add(Error.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField()).build()));
        return Case($(instanceOf(e)), () -> ErrorOutput.builder()
                .errors(errors)
                .statusCode(HttpStatus.BAD_REQUEST)
                .build());
    }

    protected <T> void validateInput(T input) {
        Set<ConstraintViolation<T>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            List<Error> errors = violations.stream()
                    .map(violation -> Error.builder()
                            .message(violation.getMessage())
                            .field(violation.getPropertyPath().toString())
                            .build())
                    .collect(Collectors.toList());
            throw new InputValidationException(createBindingResult(errors));
        }
    }

    protected BindingResult createBindingResult(List<Error> errors) {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "input");
        for (Error error : errors) {
            bindingResult.addError(new FieldError("input", error.getField(), error.getMessage()));
        }
        return bindingResult;
    }



}
