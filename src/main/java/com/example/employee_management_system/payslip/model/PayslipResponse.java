package com.example.employee_management_system.payslip.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PayslipResponse(
    Long id,
    Long employeeId,
    String employeeName,
    String payPeriod,
    BigDecimal basicSalary,
    BigDecimal allowances,
    BigDecimal deductions,
    BigDecimal netSalary,
    String status,
    LocalDateTime generatedAt,
    String storagePath
) {
}
