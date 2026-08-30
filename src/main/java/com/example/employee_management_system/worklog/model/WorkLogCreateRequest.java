package com.example.employee_management_system.worklog.model;

import com.example.employee_management_system.worklog.enums.WorkStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record WorkLogCreateRequest(
    Long employeeId,
    LocalDate workDate,
    String plannedTasks,
    @NotBlank(message = "Completed tasks are required")
    String completedTasks,
    String blockers,
    @NotNull(message = "Work status is required")
    WorkStatus status,
    String managerRemarks
) {
}
