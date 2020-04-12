package com.uniso.video.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeValidator implements ConstraintValidator<TimeValid, String> {


    @Override
    public void initialize(TimeValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss.[SS]");
            LocalTime.parse(value, f);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
