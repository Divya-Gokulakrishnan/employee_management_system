package com.example.employee_management_system.employee.service;

import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.core.auth.repository.UserAccountRepository;
import com.example.employee_management_system.department.entity.Department;
import com.example.employee_management_system.department.service.DepartmentService;
import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.employee.model.EmployeeCreateRequest;
import com.example.employee_management_system.employee.model.EmployeeListResponse;
import com.example.employee_management_system.employee.model.EmployeeResponse;
import com.example.employee_management_system.employee.model.EmployeeUpdateRequest;
import com.example.employee_management_system.employee.mapper.EmployeeMapper;
import com.example.employee_management_system.employee.repository.EmployeeRepository;
import com.example.employee_management_system.employee.specification.EmployeeSpecification;
import com.example.employee_management_system.role.entity.Role;
import com.example.employee_management_system.employee.enums.EmployeeStatus;
import com.example.employee_management_system.core.handler.BadRequestException;
import com.example.employee_management_system.core.handler.ResourceNotFoundException;
import com.example.employee_management_system.role.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {
        validateUniqueness(request, null);
        if (userAccountRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username already exists");
        }

        Employee employee = new Employee();
        applyEmployeeDetails(employee, request);
        Employee savedEmployee = employeeRepository.save(employee);

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(request.username());
        userAccount.setPasswordHash(passwordEncoder.encode(request.password()));
        userAccount.setActive(request.active() == null || request.active());
        userAccount.setEmployee(savedEmployee);
        List<Role> roles = roleRepository.findByNameIn(request.roles());
        if (roles.size() != request.roles().size()) {
            throw new ResourceNotFoundException("One or more roles were not found");
        }
        userAccount.getRoles().addAll(roles);
        userAccountRepository.save(userAccount);
        savedEmployee.setUserAccount(userAccount);

        return employeeMapper.toResponse(savedEmployee);
    }

    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeUpdateRequest request) {
        Employee employee = getEmployeeEntity(id);
        validateUniqueness(request, id);
        UserAccount userAccount = employee.getUserAccount();
        if (userAccount != null && !userAccount.getUsername().equals(request.username()) && userAccountRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username already exists");
        }

        applyEmployeeDetails(employee, request);
        Employee savedEmployee = employeeRepository.save(employee);

        if (userAccount == null) {
            userAccount = new UserAccount();
            userAccount.setEmployee(savedEmployee);
        }
        userAccount.setUsername(request.username());
        userAccount.setPasswordHash(passwordEncoder.encode(request.password()));
        userAccount.setActive(request.active() == null || request.active());
        userAccount.getRoles().clear();
        List<Role> roles = roleRepository.findByNameIn(request.roles());
        if (roles.size() != request.roles().size()) {
            throw new ResourceNotFoundException("One or more roles were not found");
        }
        userAccount.getRoles().addAll(roles);
        userAccountRepository.save(userAccount);
        savedEmployee.setUserAccount(userAccount);

        return employeeMapper.toResponse(savedEmployee);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployee(Long id) {
        return employeeMapper.toResponse(getEmployeeEntity(id));
    }

    @Transactional(readOnly = true)
    public List<EmployeeListResponse> getEmployees(String keyword, Long departmentId, EmployeeStatus status) {
        return employeeRepository.findAll(EmployeeSpecification.withFilters(keyword, departmentId, status))
            .stream()
            .map(employeeMapper::toListResponse)
            .toList();
    }

    @Transactional
    public EmployeeResponse assignDepartment(Long employeeId, Long departmentId) {
        Employee employee = getEmployeeEntity(employeeId);
        Department department = departmentService.getDepartmentEntity(departmentId);
        employee.setDepartment(department);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeEntity(id);
        employee.setActive(false);
        employee.setStatus(EmployeeStatus.INACTIVE);
        employeeRepository.save(employee);
        if (employee.getUserAccount() != null) {
            employee.getUserAccount().setActive(false);
            userAccountRepository.save(employee.getUserAccount());
        }
    }

    public Employee getEmployeeEntity(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    private void validateUniqueness(EmployeeCreateRequest request, Long employeeId) {
        employeeRepository.findByEmployeeCode(request.employeeCode())
            .filter(existing -> !existing.getId().equals(employeeId))
            .ifPresent(existing -> {
                throw new BadRequestException("Employee code already exists");
            });

        employeeRepository.findAll().stream()
            .filter(existing -> !existing.getId().equals(employeeId))
            .filter(existing ->
                existing.getOfficialEmail().equalsIgnoreCase(request.officialEmail())
                    || existing.getPersonalEmail().equalsIgnoreCase(request.personalEmail())
                    || existing.getPhoneNumber().equals(request.phoneNumber()))
            .findFirst()
            .ifPresent(existing -> {
                throw new BadRequestException("Employee email or phone number already exists");
            });
    }

    private void validateUniqueness(EmployeeUpdateRequest request, Long employeeId) {
        employeeRepository.findByEmployeeCode(request.employeeCode())
            .filter(existing -> !existing.getId().equals(employeeId))
            .ifPresent(existing -> {
                throw new BadRequestException("Employee code already exists");
            });

        employeeRepository.findAll().stream()
            .filter(existing -> !existing.getId().equals(employeeId))
            .filter(existing ->
                existing.getOfficialEmail().equalsIgnoreCase(request.officialEmail())
                    || existing.getPersonalEmail().equalsIgnoreCase(request.personalEmail())
                    || existing.getPhoneNumber().equals(request.phoneNumber()))
            .findFirst()
            .ifPresent(existing -> {
                throw new BadRequestException("Employee email or phone number already exists");
            });
    }

    private void applyEmployeeDetails(Employee employee, EmployeeCreateRequest request) {
        applyEmployeeDetails(
            employee,
            request.employeeCode(),
            request.firstName(),
            request.lastName(),
            request.gender(),
            request.personalEmail(),
            request.officialEmail(),
            request.phoneNumber(),
            request.dateOfBirth(),
            request.joiningDate(),
            request.employmentType(),
            request.status(),
            request.designation(),
            request.workLocation(),
            request.address(),
            request.emergencyContactName(),
            request.emergencyContactPhone(),
            request.basicSalary(),
            request.allowances(),
            request.deductions(),
            request.bankName(),
            request.bankAccountNumber(),
            request.taxId(),
            request.departmentId(),
            request.managerId(),
            request.active()
        );
    }

    private void applyEmployeeDetails(Employee employee, EmployeeUpdateRequest request) {
        applyEmployeeDetails(
            employee,
            request.employeeCode(),
            request.firstName(),
            request.lastName(),
            request.gender(),
            request.personalEmail(),
            request.officialEmail(),
            request.phoneNumber(),
            request.dateOfBirth(),
            request.joiningDate(),
            request.employmentType(),
            request.status(),
            request.designation(),
            request.workLocation(),
            request.address(),
            request.emergencyContactName(),
            request.emergencyContactPhone(),
            request.basicSalary(),
            request.allowances(),
            request.deductions(),
            request.bankName(),
            request.bankAccountNumber(),
            request.taxId(),
            request.departmentId(),
            request.managerId(),
            request.active()
        );
    }

    private void applyEmployeeDetails(
        Employee employee,
        String employeeCode,
        String firstName,
        String lastName,
        com.example.employee_management_system.employee.enums.Gender gender,
        String personalEmail,
        String officialEmail,
        String phoneNumber,
        java.time.LocalDate dateOfBirth,
        java.time.LocalDate joiningDate,
        com.example.employee_management_system.employee.enums.EmploymentType employmentType,
        EmployeeStatus status,
        String designation,
        String workLocation,
        String address,
        String emergencyContactName,
        String emergencyContactPhone,
        java.math.BigDecimal basicSalary,
        java.math.BigDecimal allowances,
        java.math.BigDecimal deductions,
        String bankName,
        String bankAccountNumber,
        String taxId,
        Long departmentId,
        Long managerId,
        Boolean active
    ) {
        employee.setEmployeeCode(employeeCode);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setGender(gender);
        employee.setPersonalEmail(personalEmail);
        employee.setOfficialEmail(officialEmail);
        employee.setPhoneNumber(phoneNumber);
        employee.setDateOfBirth(dateOfBirth);
        employee.setJoiningDate(joiningDate);
        employee.setEmploymentType(employmentType);
        employee.setStatus(status);
        employee.setDesignation(designation);
        employee.setWorkLocation(workLocation);
        employee.setAddress(address);
        employee.setEmergencyContactName(emergencyContactName);
        employee.setEmergencyContactPhone(emergencyContactPhone);
        employee.setBasicSalary(basicSalary);
        employee.setAllowances(allowances);
        employee.setDeductions(deductions);
        employee.setBankName(bankName);
        employee.setBankAccountNumber(bankAccountNumber);
        employee.setTaxId(taxId);
        employee.setActive(active == null || active);
        employee.setDepartment(departmentId != null ? departmentService.getDepartmentEntity(departmentId) : null);
        employee.setManager(managerId != null ? getEmployeeEntity(managerId) : null);
    }
}
