package com.example.employee_management_system.payslip.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PayslipCreateRequest(
    @NotNull(message = "Employee id is required")
    Long employeeId,
    @NotBlank(message = "Pay period is required in yyyy-MM format")
    String payPeriod,
    @NotNull(message = "Basic salary is required")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal basicSalary,
    @NotNull(message = "Allowances are required")
    @DecimalMin(value = "0.0", inclusive = true)
    BigDecimal allowances,
    @NotNull(message = "Deductions are required")
    @DecimalMin(value = "0.0", inclusive = true)
    BigDecimal deductions,
    String storagePath
) {
}
