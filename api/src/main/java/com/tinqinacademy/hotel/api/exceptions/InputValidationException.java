package com.tinqinacademy.hotel.api.exceptions;

import com.tinqinacademy.hotel.api.errors.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InputValidationException  extends RuntimeException {
   private List<Error> errors;


}
