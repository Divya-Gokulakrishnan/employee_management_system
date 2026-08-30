package com.example.employee_management_system.worklog.model;

import java.time.LocalDate;

public record WorkLogResponse(
    Long id,
    Long employeeId,
    String employeeName,
    LocalDate workDate,
    String plannedTasks,
    String completedTasks,
    String blockers,
    String status,
    String managerRemarks
) {
}
