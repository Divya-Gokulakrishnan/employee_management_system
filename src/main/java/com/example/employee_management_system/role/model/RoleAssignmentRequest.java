package com.example.employee_management_system.role.model;

import com.example.employee_management_system.role.enums.RoleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record RoleAssignmentRequest(
    @NotNull(message = "User account id is required")
    Long userAccountId,
    @NotEmpty(message = "At least one role is required")
    Set<RoleType> roles
) {
}
