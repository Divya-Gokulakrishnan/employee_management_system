package com.example.employee_management_system.payslip.controller;

import com.example.employee_management_system.payslip.model.PayslipCreateRequest;
import com.example.employee_management_system.payslip.model.PayslipListResponse;
import com.example.employee_management_system.payslip.model.PayslipResponse;
import com.example.employee_management_system.payslip.model.PayslipUpdateRequest;
import com.example.employee_management_system.payslip.service.PayslipService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payslips")
@RequiredArgsConstructor
public class PayslipController {

    private final PayslipService payslipService;

    @PostMapping
    public ResponseEntity<PayslipResponse> createPayslip(@Valid @RequestBody PayslipCreateRequest request) {
        return ResponseEntity.ok(payslipService.createPayslip(request));
    }

    @PutMapping("/{payslipId}")
    public ResponseEntity<PayslipResponse> updatePayslip(@PathVariable Long payslipId, @Valid @RequestBody PayslipUpdateRequest request) {
        return ResponseEntity.ok(payslipService.updatePayslip(payslipId, request));
    }

    @GetMapping("/{payslipId}")
    public ResponseEntity<PayslipResponse> getPayslip(@PathVariable Long payslipId) {
        return ResponseEntity.ok(payslipService.getPayslip(payslipId));
    }

    @GetMapping
    public ResponseEntity<List<PayslipListResponse>> getPayslips(
        @RequestParam(required = false) Long employeeId,
        @RequestParam(required = false) String payPeriod
    ) {
        return ResponseEntity.ok(payslipService.getPayslips(employeeId, payPeriod));
    }
}
