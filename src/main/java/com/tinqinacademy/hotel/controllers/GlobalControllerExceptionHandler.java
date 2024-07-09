package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.errorhandler.ErrorOutput;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {HotelController.class, SystemController.class})
@Slf4j
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {
    private final ErrorHandler errorHandler;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorOutput> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             HttpServletRequest request) {
        log.error("Request {} raised MethodArgumentNotValidException {}", request.getRequestURL(), ex.getBody());

        return new ResponseEntity<>(errorHandler.handle(ex), HttpStatus.BAD_REQUEST);
    }
}
