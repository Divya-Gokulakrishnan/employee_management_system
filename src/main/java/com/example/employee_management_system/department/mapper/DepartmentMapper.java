package com.example.employee_management_system.department.mapper;

import com.example.employee_management_system.department.entity.Department;
import com.example.employee_management_system.department.model.DepartmentListResponse;
import com.example.employee_management_system.department.model.DepartmentResponse;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentResponse toResponse(Department department) {
        Long managerId = department.getManager() != null ? department.getManager().getId() : null;
        String managerName = department.getManager() != null
            ? department.getManager().getFirstName() + " " + department.getManager().getLastName()
            : null;
        return new DepartmentResponse(
            department.getId(),
            department.getCode(),
            department.getName(),
            department.getDescription(),
            department.isActive(),
            managerId,
            managerName
        );
    }

    public DepartmentListResponse toListResponse(Department department) {
        Long managerId = department.getManager() != null ? department.getManager().getId() : null;
        String managerName = department.getManager() != null
            ? department.getManager().getFirstName() + " " + department.getManager().getLastName()
            : null;
        return new DepartmentListResponse(
            department.getId(),
            department.getCode(),
            department.getName(),
            department.getDescription(),
            department.isActive(),
            managerId,
            managerName
        );
    }
}
