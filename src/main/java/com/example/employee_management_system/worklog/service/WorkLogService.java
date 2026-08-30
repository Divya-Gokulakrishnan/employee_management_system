package com.example.employee_management_system.worklog.service;

import com.example.employee_management_system.core.auth.service.CurrentUserService;
import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.employee.service.EmployeeService;
import com.example.employee_management_system.worklog.entity.WorkLog;
import com.example.employee_management_system.worklog.mapper.WorkLogMapper;
import com.example.employee_management_system.worklog.model.WorkLogCreateRequest;
import com.example.employee_management_system.worklog.model.WorkLogListResponse;
import com.example.employee_management_system.worklog.model.WorkLogResponse;
import com.example.employee_management_system.worklog.model.WorkLogUpdateRequest;
import com.example.employee_management_system.worklog.repository.WorkLogRepository;
import com.example.employee_management_system.worklog.specification.WorkLogSpecification;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final EmployeeService employeeService;
    private final CurrentUserService currentUserService;
    private final WorkLogMapper workLogMapper;

    @Transactional
    public WorkLogResponse saveWorkLog(WorkLogCreateRequest request) {
        Long employeeId = currentUserService.resolveEmployeeId(request.employeeId());
        Employee employee = employeeService.getEmployeeEntity(employeeId);
        LocalDate workDate = request.workDate() != null ? request.workDate() : LocalDate.now();
        WorkLog workLog = workLogRepository.findByEmployeeIdAndWorkDate(employeeId, workDate).orElseGet(WorkLog::new);
        workLog.setEmployee(employee);
        workLog.setWorkDate(workDate);
        workLog.setPlannedTasks(request.plannedTasks());
        workLog.setCompletedTasks(request.completedTasks());
        workLog.setBlockers(request.blockers());
        workLog.setStatus(request.status());
        workLog.setManagerRemarks(request.managerRemarks());
        return workLogMapper.toResponse(workLogRepository.save(workLog));
    }

    @Transactional(readOnly = true)
    public List<WorkLogListResponse> getWorkLogs(Long employeeId, LocalDate workDate, com.example.employee_management_system.worklog.enums.WorkStatus status) {
        Long resolvedEmployeeId = currentUserService.hasAnyRole(
            com.example.employee_management_system.role.enums.RoleType.ADMIN,
            com.example.employee_management_system.role.enums.RoleType.HR,
            com.example.employee_management_system.role.enums.RoleType.MANAGER
        ) ? employeeId : currentUserService.resolveEmployeeId(employeeId);

        return workLogRepository.findAll(WorkLogSpecification.withFilters(resolvedEmployeeId, workDate, status))
            .stream()
            .map(workLogMapper::toListResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public WorkLogResponse getWorkLog(Long workLogId) {
        WorkLog workLog = workLogRepository.findById(workLogId)
            .orElseThrow(() -> new com.example.employee_management_system.core.handler.ResourceNotFoundException("Work log not found"));
        return workLogMapper.toResponse(workLog);
    }

    @Transactional
    public WorkLogResponse updateWorkLog(Long workLogId, WorkLogUpdateRequest request) {
        WorkLog workLog = workLogRepository.findById(workLogId)
            .orElseThrow(() -> new com.example.employee_management_system.core.handler.ResourceNotFoundException("Work log not found"));
        if (request.workDate() != null) {
            workLog.setWorkDate(request.workDate());
        }
        workLog.setPlannedTasks(request.plannedTasks());
        workLog.setCompletedTasks(request.completedTasks());
        workLog.setBlockers(request.blockers());
        workLog.setStatus(request.status());
        workLog.setManagerRemarks(request.managerRemarks());
        return workLogMapper.toResponse(workLogRepository.save(workLog));
    }
}
