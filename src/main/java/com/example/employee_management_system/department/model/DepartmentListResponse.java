package com.example.employee_management_system.department.model;

public record DepartmentListResponse(
    Long id,
    String code,
    String name,
    String description,
    boolean active,
    Long managerEmployeeId,
    String managerName
) {
}
