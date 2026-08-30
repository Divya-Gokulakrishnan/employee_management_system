package com.example.employee_management_system.worklog.entity;

import com.example.employee_management_system.core.common.entity.BaseAuditableEntity;
import com.example.employee_management_system.employee.entity.Employee;
import com.example.employee_management_system.worklog.enums.WorkStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "work_logs")
public class WorkLog extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate workDate;

    @Column(length = 1000)
    private String plannedTasks;

    @Column(nullable = false, length = 1000)
    private String completedTasks;

    @Column(length = 1000)
    private String blockers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WorkStatus status;

    @Column(length = 500)
    private String managerRemarks;
}
