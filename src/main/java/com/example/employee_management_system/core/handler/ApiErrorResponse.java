package com.example.employee_management_system.core.handler;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    List<String> details,
    String path
) {
}
