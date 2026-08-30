package com.example.employee_management_system.worklog.mapper;

import com.example.employee_management_system.worklog.entity.WorkLog;
import com.example.employee_management_system.worklog.model.WorkLogListResponse;
import com.example.employee_management_system.worklog.model.WorkLogResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkLogMapper {

    public WorkLogResponse toResponse(WorkLog workLog) {
        return new WorkLogResponse(
            workLog.getId(),
            workLog.getEmployee().getId(),
            workLog.getEmployee().getFirstName() + " " + workLog.getEmployee().getLastName(),
            workLog.getWorkDate(),
            workLog.getPlannedTasks(),
            workLog.getCompletedTasks(),
            workLog.getBlockers(),
            workLog.getStatus().name(),
            workLog.getManagerRemarks()
        );
    }

    public WorkLogListResponse toListResponse(WorkLog workLog) {
        return new WorkLogListResponse(
            workLog.getId(),
            workLog.getEmployee().getId(),
            workLog.getEmployee().getFirstName() + " " + workLog.getEmployee().getLastName(),
            workLog.getWorkDate(),
            workLog.getStatus().name(),
            workLog.getCompletedTasks()
        );
    }
}
