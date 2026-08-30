package com.example.employee_management_system.employee.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record EmployeeListResponse(
    Long id,
    String employeeCode,
    String fullName,
    String officialEmail,
    String phoneNumber,
    String designation,
    String status,
    String departmentName,
    String username,
    boolean active
) {
}
