package com.example.employee_management_system.payslip.specification;

import com.example.employee_management_system.payslip.entity.Payslip;
import org.springframework.data.jpa.domain.Specification;

public final class PayslipSpecification {

    private PayslipSpecification() {
    }

    public static Specification<Payslip> withFilters(Long employeeId, String payPeriod) {
        return Specification.where(hasEmployee(employeeId))
            .and(hasPayPeriod(payPeriod));
    }

    private static Specification<Payslip> hasEmployee(Long employeeId) {
        return (root, query, criteriaBuilder) ->
            employeeId == null ? null : criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }

    private static Specification<Payslip> hasPayPeriod(String payPeriod) {
        return (root, query, criteriaBuilder) ->
            payPeriod == null || payPeriod.isBlank() ? null : criteriaBuilder.equal(root.get("payPeriod"), payPeriod.trim());
    }
}
