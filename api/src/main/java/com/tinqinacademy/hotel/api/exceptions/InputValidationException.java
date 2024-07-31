package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.operations.errors.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InputValidationException  extends RuntimeException {
   private List<Error> errors;


}
