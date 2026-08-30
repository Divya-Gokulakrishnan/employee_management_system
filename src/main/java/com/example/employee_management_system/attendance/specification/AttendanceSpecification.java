package com.example.employee_management_system.attendance.specification;

import com.example.employee_management_system.attendance.entity.Attendance;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public final class AttendanceSpecification {

    private AttendanceSpecification() {
    }

    public static Specification<Attendance> withFilters(Long employeeId, LocalDate fromDate, LocalDate toDate) {
        return Specification.where(hasEmployee(employeeId))
            .and(attendanceFrom(fromDate))
            .and(attendanceTo(toDate));
    }

    private static Specification<Attendance> hasEmployee(Long employeeId) {
        return (root, query, criteriaBuilder) ->
            employeeId == null ? null : criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }

    private static Specification<Attendance> attendanceFrom(LocalDate fromDate) {
        return (root, query, criteriaBuilder) ->
            fromDate == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("attendanceDate"), fromDate);
    }

    private static Specification<Attendance> attendanceTo(LocalDate toDate) {
        return (root, query, criteriaBuilder) ->
            toDate == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("attendanceDate"), toDate);
    }
}
