package com.tinqinacademy.hotel.core.converters;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public abstract class AbstractConverter<S, T> implements Converter<S, T> {

    @Override
    public T convert(@NonNull S source) {
        log.info("Start convert from {} to {}, input: {}", source.getClass().getSimpleName(),
                getTargetClass().getSimpleName(), source);
        T target = doConvert(source);
        log.info("End convert from {} to {}, output: {}", source.getClass().getSimpleName(),
                getTargetClass().getSimpleName(), target);
        return target;
    }


    protected abstract Class<T> getTargetClass();

    protected abstract T doConvert(S source);

}
