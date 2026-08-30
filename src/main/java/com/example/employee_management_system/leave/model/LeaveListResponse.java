package com.example.employee_management_system.leave.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeaveListResponse(
    Long id,
    Long employeeId,
    String employeeName,
    String leaveType,
    LocalDate startDate,
    LocalDate endDate,
    String status
) {
}
