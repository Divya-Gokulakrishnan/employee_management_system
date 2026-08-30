package com.example.employee_management_system.core.auth.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Username is required")
    String username,
    @NotBlank(message = "Password is required")
    String password
) {
}
