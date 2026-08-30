package com.example.employee_management_system.leave.model;

import com.example.employee_management_system.leave.enums.LeaveType;
import com.example.employee_management_system.leave.validation.ValidLeaveDateRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@ValidLeaveDateRange
public record LeaveCreateRequest(
    Long employeeId,
    @NotNull(message = "Leave type is required")
    LeaveType leaveType,
    @NotNull(message = "Start date is required")
    LocalDate startDate,
    @NotNull(message = "End date is required")
    LocalDate endDate,
    @NotBlank(message = "Reason is required")
    String reason
) {
}
