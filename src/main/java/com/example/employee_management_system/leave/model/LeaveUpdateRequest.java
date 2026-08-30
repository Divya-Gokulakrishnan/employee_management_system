package com.example.employee_management_system.leave.model;

import com.example.employee_management_system.leave.enums.LeaveStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LeaveUpdateRequest(
    @NotNull(message = "Review status is required")
    LeaveStatus status,
    @NotBlank(message = "Reviewer comments are required")
    String reviewerComments
) {
}
