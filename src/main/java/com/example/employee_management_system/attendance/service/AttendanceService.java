package com.example.employee_management_system.attendance.service;

import com.example.employee_management_system.attendance.entity.Attendance;
import com.example.employee_management_system.attendance.mapper.AttendanceMapper;
import com.example.employee_management_system.attendance.model.AttendanceCreateRequest;
import com.example.employee_management_system.attendance.model.AttendanceListResponse;
import com.example.employee_management_system.attendance.model.AttendanceResponse;
import com.example.employee_management_system.attendance.model.AttendanceUpdateRequest;
import com.example.employee_management_system.attendance.repository.AttendanceRepository;
import com.example.employee_management_system.attendance.specification.AttendanceSpecification;
import com.example.employee_management_system.core.auth.service.CurrentUserService;
import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.employee.service.EmployeeService;
import com.example.employee_management_system.core.handler.BadRequestException;
import com.example.employee_management_system.core.handler.ResourceNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeService employeeService;
    private final CurrentUserService currentUserService;
    private final AttendanceMapper attendanceMapper;

    @Transactional
    public AttendanceResponse checkIn(AttendanceCreateRequest request) {
        Long employeeId = currentUserService.resolveEmployeeId(request.employeeId());
        Employee employee = employeeService.getEmployeeEntity(employeeId);
        LocalDate attendanceDate = request.attendanceDate() != null ? request.attendanceDate() : LocalDate.now();
        if (attendanceRepository.findByEmployeeIdAndAttendanceDate(employeeId, attendanceDate).isPresent()) {
            throw new BadRequestException("Attendance already exists for this employee and date");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setCheckInTime(request.checkInTime() != null ? request.checkInTime() : LocalDateTime.now());
        attendance.setStatus(request.status());
        attendance.setRemarks(request.remarks());
        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Transactional
    public AttendanceResponse checkOut(Long attendanceId, AttendanceUpdateRequest request) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        Long currentEmployeeId = currentUserService.resolveEmployeeId(attendance.getEmployee().getId());
        if (!currentEmployeeId.equals(attendance.getEmployee().getId())) {
            throw new BadRequestException("Attendance does not belong to the current user");
        }
        LocalDateTime checkOutTime = request.checkOutTime();
        if (attendance.getCheckInTime() != null && checkOutTime.isBefore(attendance.getCheckInTime())) {
            throw new BadRequestException("Check out time cannot be before check in time");
        }
        attendance.setCheckOutTime(checkOutTime);
        attendance.setRemarks(request.remarks());
        if (attendance.getCheckInTime() != null) {
            Duration duration = Duration.between(attendance.getCheckInTime(), checkOutTime);
            attendance.setTotalHours("%02d:%02d".formatted(duration.toHours(), duration.toMinutesPart()));
        }
        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Transactional(readOnly = true)
    public List<AttendanceListResponse> getAttendance(Long employeeId, LocalDate fromDate, LocalDate toDate) {
        Long resolvedEmployeeId = currentUserService.hasAnyRole(
            com.example.employee_management_system.role.enums.RoleType.ADMIN,
            com.example.employee_management_system.role.enums.RoleType.HR,
            com.example.employee_management_system.role.enums.RoleType.MANAGER
        ) ? employeeId : currentUserService.resolveEmployeeId(employeeId);

        return attendanceRepository.findAll(AttendanceSpecification.withFilters(resolvedEmployeeId, fromDate, toDate))
            .stream()
            .map(attendanceMapper::toListResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public AttendanceResponse getAttendanceById(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));
        return attendanceMapper.toResponse(attendance);
    }
}
