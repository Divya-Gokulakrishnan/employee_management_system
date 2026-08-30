package com.example.employee_management_system.role.repository;

import com.example.employee_management_system.role.entity.Role;
import com.example.employee_management_system.role.enums.RoleType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);

    List<Role> findByNameIn(Collection<RoleType> names);
}
