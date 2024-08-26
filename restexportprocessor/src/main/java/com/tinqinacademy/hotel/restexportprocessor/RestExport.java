package com.tinqinacademy.hotel.restexportprocessor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface RestExport {}