package com.example.employee_management_system.payslip.mapper;

import com.example.employee_management_system.payslip.entity.Payslip;
import com.example.employee_management_system.payslip.model.PayslipListResponse;
import com.example.employee_management_system.payslip.model.PayslipResponse;
import org.springframework.stereotype.Component;

@Component
public class PayslipMapper {

    public PayslipResponse toResponse(Payslip payslip) {
        return new PayslipResponse(
            payslip.getId(),
            payslip.getEmployee().getId(),
            payslip.getEmployee().getFirstName() + " " + payslip.getEmployee().getLastName(),
            payslip.getPayPeriod(),
            payslip.getBasicSalary(),
            payslip.getAllowances(),
            payslip.getDeductions(),
            payslip.getNetSalary(),
            payslip.getStatus().name(),
            payslip.getGeneratedAt(),
            payslip.getStoragePath()
        );
    }

    public PayslipListResponse toListResponse(Payslip payslip) {
        return new PayslipListResponse(
            payslip.getId(),
            payslip.getEmployee().getId(),
            payslip.getEmployee().getFirstName() + " " + payslip.getEmployee().getLastName(),
            payslip.getPayPeriod(),
            payslip.getNetSalary(),
            payslip.getStatus().name(),
            payslip.getGeneratedAt()
        );
    }
}
