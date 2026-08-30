package com.example.employee_management_system.role.controller;

import com.example.employee_management_system.core.auth.model.UserSummaryResponse;
import com.example.employee_management_system.role.model.RoleAssignmentRequest;
import com.example.employee_management_system.role.model.RoleResponse;
import com.example.employee_management_system.role.service.RoleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/assign")
    public ResponseEntity<UserSummaryResponse> assignRoles(@Valid @RequestBody RoleAssignmentRequest request) {
        return ResponseEntity.ok(roleService.assignRoles(request));
    }
}
