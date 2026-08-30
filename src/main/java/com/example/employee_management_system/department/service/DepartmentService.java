package com.example.employee_management_system.department.service;

import com.example.employee_management_system.department.entity.Department;
import com.example.employee_management_system.department.model.DepartmentCreateRequest;
import com.example.employee_management_system.department.model.DepartmentListResponse;
import com.example.employee_management_system.department.model.DepartmentResponse;
import com.example.employee_management_system.department.model.DepartmentUpdateRequest;
import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.core.handler.BadRequestException;
import com.example.employee_management_system.core.handler.ResourceNotFoundException;
import com.example.employee_management_system.department.mapper.DepartmentMapper;
import com.example.employee_management_system.department.repository.DepartmentRepository;
import com.example.employee_management_system.employee.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;

    @Transactional
    public DepartmentResponse createDepartment(DepartmentCreateRequest request) {
        departmentRepository.findByCode(request.code()).ifPresent(existing -> {
            throw new BadRequestException("Department code already exists");
        });
        Department department = new Department();
        applyDepartmentDetails(department, request);
        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    @Transactional
    public DepartmentResponse updateDepartment(Long id, DepartmentUpdateRequest request) {
        Department department = getDepartmentEntity(id);
        if (!department.getCode().equals(request.code())) {
            departmentRepository.findByCode(request.code()).ifPresent(existing -> {
                throw new BadRequestException("Department code already exists");
            });
        }
        applyDepartmentDetails(department, request);
        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    @Transactional(readOnly = true)
    public DepartmentResponse getDepartment(Long id) {
        return departmentMapper.toResponse(getDepartmentEntity(id));
    }

    @Transactional(readOnly = true)
    public List<DepartmentListResponse> getAllDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::toListResponse).toList();
    }

    @Transactional
    public void deleteDepartment(Long id) {
        Department department = getDepartmentEntity(id);
        department.setActive(false);
        departmentRepository.save(department);
    }

    public Department getDepartmentEntity(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

    private void applyDepartmentDetails(Department department, DepartmentCreateRequest request) {
        applyDepartmentDetails(
            department,
            request.code(),
            request.name(),
            request.description(),
            request.managerEmployeeId(),
            request.active()
        );
    }

    private void applyDepartmentDetails(Department department, DepartmentUpdateRequest request) {
        applyDepartmentDetails(
            department,
            request.code(),
            request.name(),
            request.description(),
            request.managerEmployeeId(),
            request.active()
        );
    }

    private void applyDepartmentDetails(
        Department department,
        String code,
        String name,
        String description,
        Long managerEmployeeId,
        Boolean active
    ) {
        department.setCode(code);
        department.setName(name);
        department.setDescription(description);
        department.setActive(active == null || active);
        if (managerEmployeeId != null) {
            Employee manager = employeeRepository.findById(managerEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager employee not found"));
            department.setManager(manager);
        } else {
            department.setManager(null);
        }
    }
}
