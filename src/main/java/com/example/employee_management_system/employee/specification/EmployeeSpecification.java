package com.example.employee_management_system.employee.specification;

import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.employee.enums.EmployeeStatus;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> withFilters(String keyword, Long departmentId, EmployeeStatus status) {
        return Specification.where(matchesKeyword(keyword))
            .and(hasDepartment(departmentId))
            .and(hasStatus(status));
    }

    private static Specification<Employee> matchesKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            String likeValue = "%" + keyword.trim().toLowerCase() + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeCode")), likeValue),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), likeValue),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), likeValue),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("officialEmail")), likeValue),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("designation")), likeValue)
            );
        };
    }

    private static Specification<Employee> hasDepartment(Long departmentId) {
        return (root, query, criteriaBuilder) ->
            departmentId == null ? null : criteriaBuilder.equal(root.get("department").get("id"), departmentId);
    }

    private static Specification<Employee> hasStatus(EmployeeStatus status) {
        return (root, query, criteriaBuilder) ->
            status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }
}
