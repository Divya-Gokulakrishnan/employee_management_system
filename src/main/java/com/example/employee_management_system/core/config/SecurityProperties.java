package com.example.employee_management_system.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
public record SecurityProperties(long sessionTokenValidityHours) {
}
