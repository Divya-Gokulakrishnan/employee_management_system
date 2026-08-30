package com.example.employee_management_system.employee.entity;

import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.core.common.entity.BaseAuditableEntity;
import com.example.employee_management_system.department.entity.Department;
import com.example.employee_management_system.employee.enums.EmployeeStatus;
import com.example.employee_management_system.employee.enums.EmploymentType;
import com.example.employee_management_system.employee.enums.Gender;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String employeeCode;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Gender gender;

    @Column(nullable = false, unique = true, length = 150)
    private String personalEmail;

    @Column(nullable = false, unique = true, length = 150)
    private String officialEmail;

    @Column(nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EmployeeStatus status;

    @Column(nullable = false, length = 120)
    private String designation;

    @Column(nullable = false, length = 120)
    private String workLocation;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false, length = 120)
    private String emergencyContactName;

    @Column(nullable = false, length = 20)
    private String emergencyContactPhone;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal basicSalary;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal allowances;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal deductions;

    @Column(nullable = false, length = 120)
    private String bankName;

    @Column(nullable = false, length = 60)
    private String bankAccountNumber;

    @Column(nullable = false, length = 30)
    private String taxId;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private UserAccount userAccount;
}
