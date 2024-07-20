package com.tinqinacademy.hotel.rest.exceptionhandlers;

import com.tinqinacademy.hotel.api.contracts.ErrorHandler;
import com.tinqinacademy.hotel.api.exceptions.BookingDateNotAvailableException;
import com.tinqinacademy.hotel.api.exceptions.EmailAlreadyExistsException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.rest.controllers.HotelController;
import com.tinqinacademy.hotel.rest.controllers.SystemController;
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
public class GlobalExceptionHandler {
    private final ErrorHandler errorHandler;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorOutput> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             HttpServletRequest request) {
        log.error("Request {} raised MethodArgumentNotValidException {}", request.getRequestURL(), ex.getBody());

        return new ResponseEntity<>(errorHandler.handle(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorOutput> handlesServerErrors(Exception ex, HttpServletRequest request) {
        log.error("Request {} raised ServerErrors {}", request.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(errorHandler.handle(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorOutput> handleResourceNotFound(Exception ex, HttpServletRequest request) {
        log.error("Request {} raised ResourceNotFoundException {}", request.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(errorHandler.handle(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorOutput> handleEmailAlreadyExists(Exception ex, HttpServletRequest request) {
        log.error("Request {} raised EmailAlreadyExists {}", request.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(errorHandler.handle(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingDateNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorOutput> handleBookingDateNotAvailable(Exception ex, HttpServletRequest request) {
        log.error("Request {} raised BookingDateNotAvailableException {}", request.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>(errorHandler.handle(ex), HttpStatus.BAD_REQUEST);
    }
}
