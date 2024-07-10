//package com.tinqinacademy.hotel.errorhandler;
//
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ErrorHandlerImpl implements ErrorHandler {
//    @Override
//    public ErrorOutput handle(Exception exception) {
//        List<Error> errors = new ArrayList<>();
//
//
//
////        exception.getBindingResult()
////                .getFieldErrors()
////                .forEach(error -> errors.add(Error.builder()
////                        .message(error.getDefaultMessage())
////                        .field(error.getField()).build()));
//
//        ErrorOutput errorOutput = ErrorOutput.builder()
//                .errors(errors)
//                .build();
//
//        return errorOutput;
//    }
//}
