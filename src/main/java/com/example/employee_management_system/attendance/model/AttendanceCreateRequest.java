package com.example.employee_management_system.attendance.model;

import com.example.employee_management_system.attendance.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceCreateRequest(
    Long employeeId,
    LocalDate attendanceDate,
    LocalDateTime checkInTime,
    @NotNull(message = "Attendance status is required")
    AttendanceStatus status,
    @Size(max = 500)
    String remarks
) {
}
