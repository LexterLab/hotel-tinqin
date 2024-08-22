package com.tinqinacademy.hotel.restexportprocessor;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface RestExport {
    RequestMethod method();
    String route();
    Class<?> output();
}