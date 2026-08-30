package com.example.employee_management_system.attendance.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record AttendanceUpdateRequest(
    @NotNull(message = "Check out time is required")
    LocalDateTime checkOutTime,
    @Size(max = 500)
    String remarks
) {
}
