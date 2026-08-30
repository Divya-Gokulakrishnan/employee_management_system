package com.example.employee_management_system.attendance.mapper;

import com.example.employee_management_system.attendance.entity.Attendance;
import com.example.employee_management_system.attendance.model.AttendanceListResponse;
import com.example.employee_management_system.attendance.model.AttendanceResponse;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public AttendanceResponse toResponse(Attendance attendance) {
        return new AttendanceResponse(
            attendance.getId(),
            attendance.getEmployee().getId(),
            attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName(),
            attendance.getAttendanceDate(),
            attendance.getCheckInTime(),
            attendance.getCheckOutTime(),
            attendance.getStatus().name(),
            attendance.getRemarks(),
            attendance.getTotalHours()
        );
    }

    public AttendanceListResponse toListResponse(Attendance attendance) {
        return new AttendanceListResponse(
            attendance.getId(),
            attendance.getEmployee().getId(),
            attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName(),
            attendance.getAttendanceDate(),
            attendance.getCheckInTime(),
            attendance.getCheckOutTime(),
            attendance.getStatus().name(),
            attendance.getRemarks(),
            attendance.getTotalHours()
        );
    }
}
