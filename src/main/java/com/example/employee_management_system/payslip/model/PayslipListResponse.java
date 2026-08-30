package com.example.employee_management_system.payslip.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PayslipListResponse(
    Long id,
    Long employeeId,
    String employeeName,
    String payPeriod,
    BigDecimal netSalary,
    String status,
    LocalDateTime generatedAt
) {
}
