package com.example.employee_management_system.role.service;

import com.example.employee_management_system.role.entity.Role;
import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.core.handler.ResourceNotFoundException;
import com.example.employee_management_system.role.mapper.RoleMapper;
import com.example.employee_management_system.core.auth.mapper.UserAccountMapper;
import com.example.employee_management_system.core.auth.model.UserSummaryResponse;
import com.example.employee_management_system.role.model.RoleAssignmentRequest;
import com.example.employee_management_system.role.model.RoleResponse;
import com.example.employee_management_system.role.repository.RoleRepository;
import com.example.employee_management_system.core.auth.repository.UserAccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final RoleMapper roleMapper;
    private final UserAccountMapper userAccountMapper;

    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
    }

    @Transactional
    public UserSummaryResponse assignRoles(RoleAssignmentRequest request) {
        UserAccount userAccount = userAccountRepository.findById(request.userAccountId())
            .orElseThrow(() -> new ResourceNotFoundException("User account not found"));
        List<Role> roles = roleRepository.findByNameIn(request.roles());
        if (roles.size() != request.roles().size()) {
            throw new ResourceNotFoundException("One or more roles were not found");
        }
        userAccount.getRoles().clear();
        userAccount.getRoles().addAll(roles);
        return userAccountMapper.toResponse(userAccountRepository.save(userAccount));
    }
}
