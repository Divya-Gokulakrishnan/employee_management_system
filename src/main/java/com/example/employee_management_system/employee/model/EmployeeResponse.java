package com.example.employee_management_system.employee.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record EmployeeResponse(
    Long id,
    String employeeCode,
    String firstName,
    String lastName,
    String fullName,
    String gender,
    String personalEmail,
    String officialEmail,
    String phoneNumber,
    LocalDate dateOfBirth,
    LocalDate joiningDate,
    String employmentType,
    String status,
    String designation,
    String workLocation,
    String address,
    String emergencyContactName,
    String emergencyContactPhone,
    BigDecimal basicSalary,
    BigDecimal allowances,
    BigDecimal deductions,
    BigDecimal netSalary,
    String bankName,
    String bankAccountNumber,
    String taxId,
    Long departmentId,
    String departmentName,
    Long managerId,
    String managerName,
    String username,
    Set<String> roles,
    boolean active
) {
}
