package com.example.employee_management_system.leave.mapper;

import com.example.employee_management_system.leave.entity.LeaveRequest;
import com.example.employee_management_system.leave.model.LeaveListResponse;
import com.example.employee_management_system.leave.model.LeaveResponse;
import org.springframework.stereotype.Component;

@Component
public class LeaveMapper {

    public LeaveResponse toResponse(LeaveRequest leaveRequest) {
        return new LeaveResponse(
            leaveRequest.getId(),
            leaveRequest.getEmployee().getId(),
            leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName(),
            leaveRequest.getLeaveType().name(),
            leaveRequest.getStartDate(),
            leaveRequest.getEndDate(),
            leaveRequest.getReason(),
            leaveRequest.getStatus().name(),
            leaveRequest.getReviewedBy() != null ? leaveRequest.getReviewedBy().getId() : null,
            leaveRequest.getReviewedBy() != null ? leaveRequest.getReviewedBy().getUsername() : null,
            leaveRequest.getReviewedAt(),
            leaveRequest.getReviewerComments()
        );
    }

    public LeaveListResponse toListResponse(LeaveRequest leaveRequest) {
        return new LeaveListResponse(
            leaveRequest.getId(),
            leaveRequest.getEmployee().getId(),
            leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName(),
            leaveRequest.getLeaveType().name(),
            leaveRequest.getStartDate(),
            leaveRequest.getEndDate(),
            leaveRequest.getStatus().name()
        );
    }
}
