package com.example.employee_management_system.payslip.model;

import com.example.employee_management_system.payslip.enums.PayslipStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PayslipUpdateRequest(
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
    PayslipStatus status,
    String storagePath
) {
}
