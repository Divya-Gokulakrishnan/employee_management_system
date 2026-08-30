package com.example.employee_management_system.worklog.controller;

import com.example.employee_management_system.worklog.enums.WorkStatus;
import com.example.employee_management_system.worklog.model.WorkLogCreateRequest;
import com.example.employee_management_system.worklog.model.WorkLogListResponse;
import com.example.employee_management_system.worklog.model.WorkLogResponse;
import com.example.employee_management_system.worklog.model.WorkLogUpdateRequest;
import com.example.employee_management_system.worklog.service.WorkLogService;
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
@RequestMapping("/api/v1/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService workLogService;

    @PostMapping
    public ResponseEntity<WorkLogResponse> saveWorkLog(@Valid @RequestBody WorkLogCreateRequest request) {
        return ResponseEntity.ok(workLogService.saveWorkLog(request));
    }

    @PutMapping("/{workLogId}")
    public ResponseEntity<WorkLogResponse> updateWorkLog(@PathVariable Long workLogId, @Valid @RequestBody WorkLogUpdateRequest request) {
        return ResponseEntity.ok(workLogService.updateWorkLog(workLogId, request));
    }

    @GetMapping("/{workLogId}")
    public ResponseEntity<WorkLogResponse> getWorkLog(@PathVariable Long workLogId) {
        return ResponseEntity.ok(workLogService.getWorkLog(workLogId));
    }

    @GetMapping
    public ResponseEntity<List<WorkLogListResponse>> getWorkLogs(
        @RequestParam(required = false) Long employeeId,
        @RequestParam(required = false) LocalDate workDate,
        @RequestParam(required = false) WorkStatus status
    ) {
        return ResponseEntity.ok(workLogService.getWorkLogs(employeeId, workDate, status));
    }
}
