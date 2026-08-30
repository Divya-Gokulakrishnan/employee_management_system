package com.example.employee_management_system.worklog.specification;

import com.example.employee_management_system.worklog.entity.WorkLog;
import com.example.employee_management_system.worklog.enums.WorkStatus;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public final class WorkLogSpecification {

    private WorkLogSpecification() {
    }

    public static Specification<WorkLog> withFilters(Long employeeId, LocalDate workDate, WorkStatus status) {
        return Specification.where(hasEmployee(employeeId))
            .and(hasWorkDate(workDate))
            .and(hasStatus(status));
    }

    private static Specification<WorkLog> hasEmployee(Long employeeId) {
        return (root, query, criteriaBuilder) ->
            employeeId == null ? null : criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }

    private static Specification<WorkLog> hasWorkDate(LocalDate workDate) {
        return (root, query, criteriaBuilder) ->
            workDate == null ? null : criteriaBuilder.equal(root.get("workDate"), workDate);
    }

    private static Specification<WorkLog> hasStatus(WorkStatus status) {
        return (root, query, criteriaBuilder) ->
            status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }
}
