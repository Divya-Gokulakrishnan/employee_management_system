package com.example.employee_management_system.payslip.service;

import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.employee.service.EmployeeService;
import com.example.employee_management_system.payslip.entity.Payslip;
import com.example.employee_management_system.payslip.enums.PayslipStatus;
import com.example.employee_management_system.payslip.mapper.PayslipMapper;
import com.example.employee_management_system.payslip.model.PayslipCreateRequest;
import com.example.employee_management_system.payslip.model.PayslipListResponse;
import com.example.employee_management_system.payslip.model.PayslipResponse;
import com.example.employee_management_system.payslip.model.PayslipUpdateRequest;
import com.example.employee_management_system.payslip.repository.PayslipRepository;
import com.example.employee_management_system.payslip.specification.PayslipSpecification;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayslipService {

    private final PayslipRepository payslipRepository;
    private final EmployeeService employeeService;
    private final PayslipMapper payslipMapper;

    @Transactional
    public PayslipResponse createPayslip(PayslipCreateRequest request) {
        Employee employee = employeeService.getEmployeeEntity(request.employeeId());
        payslipRepository.findByEmployeeIdAndPayPeriod(request.employeeId(), request.payPeriod())
            .ifPresent(existing -> {
                throw new com.example.employee_management_system.core.handler.BadRequestException("Payslip already exists for this pay period");
            });
        Payslip payslip = new Payslip();
        payslip.setEmployee(employee);
        payslip.setPayPeriod(request.payPeriod());
        payslip.setBasicSalary(request.basicSalary());
        payslip.setAllowances(request.allowances());
        payslip.setDeductions(request.deductions());
        payslip.setNetSalary(request.basicSalary().add(request.allowances()).subtract(request.deductions()));
        payslip.setStatus(PayslipStatus.GENERATED);
        payslip.setGeneratedAt(LocalDateTime.now());
        payslip.setStoragePath(request.storagePath() == null || request.storagePath().isBlank()
            ? "/payslips/" + employee.getEmployeeCode() + "-" + request.payPeriod() + ".pdf"
            : request.storagePath());
        return payslipMapper.toResponse(payslipRepository.save(payslip));
    }

    @Transactional(readOnly = true)
    public List<PayslipListResponse> getPayslips(Long employeeId, String payPeriod) {
        return payslipRepository.findAll(PayslipSpecification.withFilters(employeeId, payPeriod))
            .stream()
            .map(payslipMapper::toListResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public PayslipResponse getPayslip(Long payslipId) {
        Payslip payslip = payslipRepository.findById(payslipId)
            .orElseThrow(() -> new com.example.employee_management_system.core.handler.ResourceNotFoundException("Payslip not found"));
        return payslipMapper.toResponse(payslip);
    }

    @Transactional
    public PayslipResponse updatePayslip(Long payslipId, PayslipUpdateRequest request) {
        Payslip payslip = payslipRepository.findById(payslipId)
            .orElseThrow(() -> new com.example.employee_management_system.core.handler.ResourceNotFoundException("Payslip not found"));
        payslip.setPayPeriod(request.payPeriod());
        payslip.setBasicSalary(request.basicSalary());
        payslip.setAllowances(request.allowances());
        payslip.setDeductions(request.deductions());
        payslip.setNetSalary(request.basicSalary().add(request.allowances()).subtract(request.deductions()));
        payslip.setStatus(request.status() == null ? payslip.getStatus() : request.status());
        if (request.storagePath() != null && !request.storagePath().isBlank()) {
            payslip.setStoragePath(request.storagePath());
        }
        return payslipMapper.toResponse(payslipRepository.save(payslip));
    }
}
