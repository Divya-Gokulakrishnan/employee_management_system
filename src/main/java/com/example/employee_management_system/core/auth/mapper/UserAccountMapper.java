package com.example.employee_management_system.core.auth.mapper;

import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.core.auth.model.UserSummaryResponse;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    public UserSummaryResponse toResponse(UserAccount userAccount) {
        return new UserSummaryResponse(
            userAccount.getId(),
            userAccount.getUsername(),
            userAccount.isActive(),
            userAccount.getEmployee() != null ? userAccount.getEmployee().getId() : null,
            userAccount.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet())
        );
    }
}
