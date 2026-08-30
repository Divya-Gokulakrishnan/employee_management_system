package com.example.employee_management_system.employee.model;

import com.example.employee_management_system.employee.enums.EmployeeStatus;
import com.example.employee_management_system.employee.enums.EmploymentType;
import com.example.employee_management_system.employee.enums.Gender;
import com.example.employee_management_system.role.enums.RoleType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record EmployeeCreateRequest(
    @NotBlank(message = "Employee code is required")
    String employeeCode,
    @NotBlank(message = "First name is required")
    String firstName,
    @NotBlank(message = "Last name is required")
    String lastName,
    @NotNull(message = "Gender is required")
    Gender gender,
    @NotBlank(message = "Personal email is required")
    @Email(message = "Personal email should be valid")
    String personalEmail,
    @NotBlank(message = "Official email is required")
    @Email(message = "Official email should be valid")
    String officialEmail,
    @NotBlank(message = "Phone number is required")
    String phoneNumber,
    @NotNull(message = "Date of birth is required")
    LocalDate dateOfBirth,
    @NotNull(message = "Joining date is required")
    LocalDate joiningDate,
    @NotNull(message = "Employment type is required")
    EmploymentType employmentType,
    @NotNull(message = "Employee status is required")
    EmployeeStatus status,
    @NotBlank(message = "Designation is required")
    String designation,
    @NotBlank(message = "Work location is required")
    String workLocation,
    @NotBlank(message = "Address is required")
    String address,
    @NotBlank(message = "Emergency contact name is required")
    String emergencyContactName,
    @NotBlank(message = "Emergency contact phone is required")
    String emergencyContactPhone,
    @NotNull(message = "Basic salary is required")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal basicSalary,
    @NotNull(message = "Allowances are required")
    @DecimalMin(value = "0.0", inclusive = true)
    BigDecimal allowances,
    @NotNull(message = "Deductions are required")
    @DecimalMin(value = "0.0", inclusive = true)
    BigDecimal deductions,
    @NotBlank(message = "Bank name is required")
    String bankName,
    @NotBlank(message = "Bank account number is required")
    String bankAccountNumber,
    @NotBlank(message = "Tax id is required")
    String taxId,
    Long departmentId,
    Long managerId,
    @NotBlank(message = "Username is required")
    String username,
    @NotBlank(message = "Password is required")
    String password,
    @NotEmpty(message = "At least one role is required")
    Set<RoleType> roles,
    Boolean active
) {
}
