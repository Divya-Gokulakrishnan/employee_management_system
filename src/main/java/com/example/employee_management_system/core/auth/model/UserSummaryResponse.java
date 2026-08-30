package com.example.employee_management_system.core.auth.model;

import java.util.Set;

public record UserSummaryResponse(
    Long id,
    String username,
    boolean active,
    Long employeeId,
    Set<String> roles
) {
}
