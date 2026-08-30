package com.example.employee_management_system.role.mapper;

import com.example.employee_management_system.role.entity.Role;
import com.example.employee_management_system.role.model.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {
        return new RoleResponse(role.getId(), role.getName().name(), role.getDescription());
    }
}
