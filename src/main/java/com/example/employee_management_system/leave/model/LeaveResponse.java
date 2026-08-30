package com.example.employee_management_system.leave.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeaveResponse(
    Long id,
    Long employeeId,
    String employeeName,
    String leaveType,
    LocalDate startDate,
    LocalDate endDate,
    String reason,
    String status,
    Long reviewedById,
    String reviewedByUsername,
    LocalDateTime reviewedAt,
    String reviewerComments
) {
}
