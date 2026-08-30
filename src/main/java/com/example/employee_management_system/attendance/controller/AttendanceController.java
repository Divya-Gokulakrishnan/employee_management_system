package com.example.employee_management_system.attendance.controller;

import com.example.employee_management_system.attendance.model.AttendanceCreateRequest;
import com.example.employee_management_system.attendance.model.AttendanceListResponse;
import com.example.employee_management_system.attendance.model.AttendanceResponse;
import com.example.employee_management_system.attendance.model.AttendanceUpdateRequest;
import com.example.employee_management_system.attendance.service.AttendanceService;
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
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponse> checkIn(@Valid @RequestBody AttendanceCreateRequest request) {
        return ResponseEntity.ok(attendanceService.checkIn(request));
    }

    @PutMapping("/{attendanceId}/check-out")
    public ResponseEntity<AttendanceResponse> checkOut(
        @PathVariable Long attendanceId,
        @Valid @RequestBody AttendanceUpdateRequest request
    ) {
        return ResponseEntity.ok(attendanceService.checkOut(attendanceId, request));
    }

    @GetMapping("/{attendanceId}")
    public ResponseEntity<AttendanceResponse> getAttendanceById(@PathVariable Long attendanceId) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(attendanceId));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceListResponse>> getAttendance(
        @RequestParam(required = false) Long employeeId,
        @RequestParam(required = false) LocalDate fromDate,
        @RequestParam(required = false) LocalDate toDate
    ) {
        return ResponseEntity.ok(attendanceService.getAttendance(employeeId, fromDate, toDate));
    }
}
