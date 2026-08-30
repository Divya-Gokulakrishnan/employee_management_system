package com.example.employee_management_system.core.config;

import com.example.employee_management_system.role.entity.Role;
import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.role.enums.RoleType;
import com.example.employee_management_system.role.repository.RoleRepository;
import com.example.employee_management_system.core.auth.repository.UserAccountRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReferenceDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Arrays.stream(RoleType.values()).forEach(roleType -> roleRepository.findByName(roleType).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleType);
            role.setDescription(roleType.name() + " role");
            return roleRepository.save(role);
        }));

        userAccountRepository.findByUsername("admin").orElseGet(() -> {
            UserAccount admin = new UserAccount();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
            admin.setActive(true);
            admin.getRoles().add(roleRepository.findByName(RoleType.ADMIN).orElseThrow());
            admin.getRoles().add(roleRepository.findByName(RoleType.HR).orElseThrow());
            return userAccountRepository.save(admin);
        });
    }
}
