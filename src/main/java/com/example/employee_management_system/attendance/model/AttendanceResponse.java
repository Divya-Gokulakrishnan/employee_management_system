package com.example.employee_management_system.attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceResponse(
    Long id,
    Long employeeId,
    String employeeName,
    LocalDate attendanceDate,
    LocalDateTime checkInTime,
    LocalDateTime checkOutTime,
    String status,
    String remarks,
    String totalHours
) {
}
