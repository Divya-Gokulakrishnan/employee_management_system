package com.example.employee_management_system.leave.service;

import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.core.auth.service.CurrentUserService;
import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.employee.service.EmployeeService;
import com.example.employee_management_system.leave.entity.LeaveRequest;
import com.example.employee_management_system.leave.enums.LeaveStatus;
import com.example.employee_management_system.core.handler.BadRequestException;
import com.example.employee_management_system.core.handler.ResourceNotFoundException;
import com.example.employee_management_system.leave.mapper.LeaveMapper;
import com.example.employee_management_system.leave.model.LeaveCreateRequest;
import com.example.employee_management_system.leave.model.LeaveListResponse;
import com.example.employee_management_system.leave.model.LeaveResponse;
import com.example.employee_management_system.leave.model.LeaveUpdateRequest;
import com.example.employee_management_system.leave.repository.LeaveRequestRepository;
import com.example.employee_management_system.leave.specification.LeaveRequestSpecification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeService employeeService;
    private final CurrentUserService currentUserService;
    private final LeaveMapper leaveMapper;

    @Transactional
    public LeaveResponse createLeave(LeaveCreateRequest request) {
        Long employeeId = currentUserService.resolveEmployeeId(request.employeeId());
        Employee employee = employeeService.getEmployeeEntity(employeeId);
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(request.leaveType());
        leaveRequest.setStartDate(request.startDate());
        leaveRequest.setEndDate(request.endDate());
        leaveRequest.setReason(request.reason());
        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveMapper.toResponse(leaveRequestRepository.save(leaveRequest));
    }

    @Transactional
    public LeaveResponse reviewLeave(Long leaveId, LeaveUpdateRequest request) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        if (request.status() != LeaveStatus.APPROVED && request.status() != LeaveStatus.REJECTED) {
            throw new BadRequestException("Leave can only be approved or rejected in review");
        }
        UserAccount reviewer = currentUserService.getCurrentUserAccount();
        leaveRequest.setStatus(request.status());
        leaveRequest.setReviewerComments(request.reviewerComments());
        leaveRequest.setReviewedBy(reviewer);
        leaveRequest.setReviewedAt(LocalDateTime.now());
        return leaveMapper.toResponse(leaveRequestRepository.save(leaveRequest));
    }

    @Transactional(readOnly = true)
    public List<LeaveListResponse> getLeaves(Long employeeId, LeaveStatus status, LocalDate fromDate, LocalDate toDate) {
        Long resolvedEmployeeId = currentUserService.hasAnyRole(
            com.example.employee_management_system.role.enums.RoleType.ADMIN,
            com.example.employee_management_system.role.enums.RoleType.HR,
            com.example.employee_management_system.role.enums.RoleType.MANAGER
        ) ? employeeId : currentUserService.resolveEmployeeId(employeeId);

        return leaveRequestRepository.findAll(LeaveRequestSpecification.withFilters(resolvedEmployeeId, status, fromDate, toDate))
            .stream()
            .map(leaveMapper::toListResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public LeaveResponse getLeave(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        return leaveMapper.toResponse(leaveRequest);
    }
}
