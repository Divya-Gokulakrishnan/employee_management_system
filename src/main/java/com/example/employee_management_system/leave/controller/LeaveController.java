package com.example.employee_management_system.leave.controller;

import com.example.employee_management_system.leave.enums.LeaveStatus;
import com.example.employee_management_system.leave.model.LeaveCreateRequest;
import com.example.employee_management_system.leave.model.LeaveListResponse;
import com.example.employee_management_system.leave.model.LeaveResponse;
import com.example.employee_management_system.leave.model.LeaveUpdateRequest;
import com.example.employee_management_system.leave.service.LeaveService;
import jakarta.validation.Valid;
import java.time.LocalDate;
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
@RequestMapping("/api/v1/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<LeaveResponse> createLeave(@Valid @RequestBody LeaveCreateRequest request) {
        return ResponseEntity.ok(leaveService.createLeave(request));
    }

    @PutMapping("/{leaveId}/review")
    public ResponseEntity<LeaveResponse> reviewLeave(@PathVariable Long leaveId, @Valid @RequestBody LeaveUpdateRequest request) {
        return ResponseEntity.ok(leaveService.reviewLeave(leaveId, request));
    }

    @GetMapping("/{leaveId}")
    public ResponseEntity<LeaveResponse> getLeave(@PathVariable Long leaveId) {
        return ResponseEntity.ok(leaveService.getLeave(leaveId));
    }

    @GetMapping
    public ResponseEntity<List<LeaveListResponse>> getLeaves(
        @RequestParam(required = false) Long employeeId,
        @RequestParam(required = false) LeaveStatus status,
        @RequestParam(required = false) LocalDate fromDate,
        @RequestParam(required = false) LocalDate toDate
    ) {
        return ResponseEntity.ok(leaveService.getLeaves(employeeId, status, fromDate, toDate));
    }
}
