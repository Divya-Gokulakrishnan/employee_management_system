package com.example.employee_management_system.employee.controller;

import com.example.employee_management_system.employee.enums.EmployeeStatus;
import com.example.employee_management_system.employee.model.EmployeeCreateRequest;
import com.example.employee_management_system.employee.model.EmployeeListResponse;
import com.example.employee_management_system.employee.model.EmployeeResponse;
import com.example.employee_management_system.employee.model.EmployeeUpdateRequest;
import com.example.employee_management_system.employee.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeCreateRequest request) {
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeListResponse>> getEmployees(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Long departmentId,
        @RequestParam(required = false) EmployeeStatus status
    ) {
        return ResponseEntity.ok(employeeService.getEmployees(keyword, departmentId, status));
    }

    @PutMapping("/{id}/department/{departmentId}")
    public ResponseEntity<EmployeeResponse> assignDepartment(@PathVariable Long id, @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.assignDepartment(id, departmentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
