package com.example.employee_management_system.payslip.repository;

import com.example.employee_management_system.payslip.entity.Payslip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PayslipRepository extends JpaRepository<Payslip, Long>, JpaSpecificationExecutor<Payslip> {

    Optional<Payslip> findByEmployeeIdAndPayPeriod(Long employeeId, String payPeriod);
}
