package com.example.employee_management_system.leave.validation;

import com.example.employee_management_system.leave.model.LeaveCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LeaveDateRangeValidator implements ConstraintValidator<ValidLeaveDateRange, LeaveCreateRequest> {

    @Override
    public boolean isValid(LeaveCreateRequest value, ConstraintValidatorContext context) {
        if (value == null || value.startDate() == null || value.endDate() == null) {
            return true;
        }
        return !value.endDate().isBefore(value.startDate());
    }
}
