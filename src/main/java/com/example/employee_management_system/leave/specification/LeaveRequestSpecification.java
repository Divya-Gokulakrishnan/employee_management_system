package com.example.employee_management_system.leave.specification;

import com.example.employee_management_system.leave.entity.LeaveRequest;
import com.example.employee_management_system.leave.enums.LeaveStatus;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public final class LeaveRequestSpecification {

    private LeaveRequestSpecification() {
    }

    public static Specification<LeaveRequest> withFilters(Long employeeId, LeaveStatus status, LocalDate fromDate, LocalDate toDate) {
        return Specification.where(hasEmployee(employeeId))
            .and(hasStatus(status))
            .and(startsFrom(fromDate))
            .and(endsTo(toDate));
    }

    private static Specification<LeaveRequest> hasEmployee(Long employeeId) {
        return (root, query, criteriaBuilder) ->
            employeeId == null ? null : criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }

    private static Specification<LeaveRequest> hasStatus(LeaveStatus status) {
        return (root, query, criteriaBuilder) ->
            status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<LeaveRequest> startsFrom(LocalDate fromDate) {
        return (root, query, criteriaBuilder) ->
            fromDate == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), fromDate);
    }

    private static Specification<LeaveRequest> endsTo(LocalDate toDate) {
        return (root, query, criteriaBuilder) ->
            toDate == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), toDate);
    }
}
