package com.tinqinacademy.hotel.api.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchValidator implements ConstraintValidator<PasswordValueMatches, Object> {
    private String field;
    private String fieldMatch;
    private String message;
    @Override
    public void initialize(PasswordValueMatches constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(o)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(o)
                .getPropertyValue(fieldMatch);

        boolean isValid = false;
        if (fieldValue != null) {
            isValid = fieldValue.equals(fieldMatchValue);
        }

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field)
                    .addConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldMatch)
                    .addConstraintViolation();
        }
        return isValid;
    }
}