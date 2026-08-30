package com.example.employee_management_system.employee.mapper;

import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.employee.model.EmployeeListResponse;
import com.example.employee_management_system.employee.model.EmployeeResponse;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee) {
        UserAccount userAccount = employee.getUserAccount();
        Set<String> roles = userAccount == null
            ? Set.of()
            : userAccount.getRoles().stream().map(role -> role.getName().name()).collect(java.util.stream.Collectors.toSet());

        BigDecimal netSalary = employee.getBasicSalary()
            .add(employee.getAllowances())
            .subtract(employee.getDeductions());

        return new EmployeeResponse(
            employee.getId(),
            employee.getEmployeeCode(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getFirstName() + " " + employee.getLastName(),
            employee.getGender().name(),
            employee.getPersonalEmail(),
            employee.getOfficialEmail(),
            employee.getPhoneNumber(),
            employee.getDateOfBirth(),
            employee.getJoiningDate(),
            employee.getEmploymentType().name(),
            employee.getStatus().name(),
            employee.getDesignation(),
            employee.getWorkLocation(),
            employee.getAddress(),
            employee.getEmergencyContactName(),
            employee.getEmergencyContactPhone(),
            employee.getBasicSalary(),
            employee.getAllowances(),
            employee.getDeductions(),
            netSalary,
            employee.getBankName(),
            employee.getBankAccountNumber(),
            employee.getTaxId(),
            employee.getDepartment() != null ? employee.getDepartment().getId() : null,
            employee.getDepartment() != null ? employee.getDepartment().getName() : null,
            employee.getManager() != null ? employee.getManager().getId() : null,
            employee.getManager() != null ? employee.getManager().getFirstName() + " " + employee.getManager().getLastName() : null,
            userAccount != null ? userAccount.getUsername() : null,
            roles,
            employee.isActive()
        );
    }

    public EmployeeListResponse toListResponse(Employee employee) {
        UserAccount userAccount = employee.getUserAccount();
        return new EmployeeListResponse(
            employee.getId(),
            employee.getEmployeeCode(),
            employee.getFirstName() + " " + employee.getLastName(),
            employee.getOfficialEmail(),
            employee.getPhoneNumber(),
            employee.getDesignation(),
            employee.getStatus().name(),
            employee.getDepartment() != null ? employee.getDepartment().getName() : null,
            userAccount != null ? userAccount.getUsername() : null,
            employee.isActive()
        );
    }
}
