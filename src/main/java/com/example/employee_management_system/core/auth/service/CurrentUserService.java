package com.example.employee_management_system.core.auth.service;

import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.role.enums.RoleType;
import com.example.employee_management_system.core.handler.ForbiddenException;
import com.example.employee_management_system.core.handler.ResourceNotFoundException;
import com.example.employee_management_system.core.security.SecurityUtils;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public UserAccount getCurrentUserAccount() {
        return SecurityUtils.getCurrentUser().userAccount();
    }

    public boolean hasAnyRole(RoleType... roles) {
        Set<RoleType> currentRoles = getCurrentUserAccount().getRoles().stream()
            .map(role -> role.getName())
            .collect(Collectors.toSet());
        return Arrays.stream(roles).anyMatch(currentRoles::contains);
    }

    public Long resolveEmployeeId(Long requestedEmployeeId) {
        UserAccount currentUser = getCurrentUserAccount();
        if (requestedEmployeeId != null && hasAnyRole(RoleType.ADMIN, RoleType.HR, RoleType.MANAGER, RoleType.FINANCE)) {
            return requestedEmployeeId;
        }
        if (currentUser.getEmployee() == null) {
            throw new ResourceNotFoundException("Current user is not linked to any employee profile");
        }
        if (requestedEmployeeId != null && !requestedEmployeeId.equals(currentUser.getEmployee().getId())) {
            throw new ForbiddenException("You can only access your own employee records");
        }
        return currentUser.getEmployee().getId();
    }
}
