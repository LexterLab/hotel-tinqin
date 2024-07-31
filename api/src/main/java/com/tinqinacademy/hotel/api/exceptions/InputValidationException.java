package com.tinqinacademy.hotel.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

@Getter
@Setter
@AllArgsConstructor
public class InputValidationException  extends RuntimeException{
   private BindingResult bindingResult;
}
