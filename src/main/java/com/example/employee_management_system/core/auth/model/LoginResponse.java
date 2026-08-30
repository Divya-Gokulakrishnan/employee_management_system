package com.example.employee_management_system.core.auth.model;

import java.time.LocalDateTime;
import java.util.Set;

public record LoginResponse(
    String token,
    String username,
    Set<String> roles,
    LocalDateTime loginAt,
    LocalDateTime expiresAt
) {
}
