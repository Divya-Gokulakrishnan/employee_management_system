package com.example.employee_management_system.worklog.model;

import java.time.LocalDate;

public record WorkLogListResponse(
    Long id,
    Long employeeId,
    String employeeName,
    LocalDate workDate,
    String status,
    String completedTasks
) {
}
