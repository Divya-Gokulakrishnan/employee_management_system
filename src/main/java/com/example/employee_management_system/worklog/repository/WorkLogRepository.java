package com.example.employee_management_system.worklog.repository;

import com.example.employee_management_system.worklog.entity.WorkLog;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long>, JpaSpecificationExecutor<WorkLog> {

    Optional<WorkLog> findByEmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);
}
