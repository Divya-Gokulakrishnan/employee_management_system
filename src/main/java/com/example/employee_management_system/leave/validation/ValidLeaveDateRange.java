package com.example.employee_management_system.leave.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LeaveDateRangeValidator.class)
public @interface ValidLeaveDateRange {

    String message() default "End date must be on or after start date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
