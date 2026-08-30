package com.example.employee_management_system.department.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentUpdateRequest(
    @NotBlank(message = "Department code is required")
    @Size(max = 50)
    String code,
    @NotBlank(message = "Department name is required")
    @Size(max = 120)
    String name,
    @Size(max = 500)
    String description,
    Long managerEmployeeId,
    Boolean active
) {
}
