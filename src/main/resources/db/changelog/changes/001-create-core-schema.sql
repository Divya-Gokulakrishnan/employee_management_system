CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL
);

CREATE TABLE departments (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL UNIQUE,
    description VARCHAR(500),
    active BOOLEAN NOT NULL,
    manager_employee_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_code VARCHAR(30) NOT NULL UNIQUE,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    gender VARCHAR(30) NOT NULL,
    personal_email VARCHAR(150) NOT NULL UNIQUE,
    official_email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    date_of_birth DATE NOT NULL,
    joining_date DATE NOT NULL,
    employment_type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    designation VARCHAR(120) NOT NULL,
    work_location VARCHAR(120) NOT NULL,
    address VARCHAR(500) NOT NULL,
    emergency_contact_name VARCHAR(120) NOT NULL,
    emergency_contact_phone VARCHAR(20) NOT NULL,
    basic_salary DECIMAL(15,2) NOT NULL,
    allowances DECIMAL(15,2) NOT NULL,
    deductions DECIMAL(15,2) NOT NULL,
    bank_name VARCHAR(120) NOT NULL,
    bank_account_number VARCHAR(60) NOT NULL,
    tax_id VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL,
    department_id BIGINT,
    manager_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL
);

CREATE TABLE user_accounts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    last_login_at TIMESTAMP NULL,
    employee_id BIGINT UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL
);

CREATE TABLE user_account_roles (
    user_account_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_account_id, role_id)
);

CREATE TABLE user_sessions (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(120) NOT NULL UNIQUE,
    status VARCHAR(30) NOT NULL,
    login_at TIMESTAMP NOT NULL,
    logout_at TIMESTAMP NULL,
    expires_at TIMESTAMP NOT NULL,
    user_account_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL
);

CREATE TABLE attendance (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    check_in_time TIMESTAMP NULL,
    check_out_time TIMESTAMP NULL,
    status VARCHAR(30) NOT NULL,
    remarks VARCHAR(500),
    total_hours VARCHAR(30),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    CONSTRAINT uk_attendance_employee_date UNIQUE (employee_id, attendance_date)
);

CREATE TABLE leave_requests (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    leave_type VARCHAR(30) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(1000) NOT NULL,
    status VARCHAR(30) NOT NULL,
    reviewed_by BIGINT,
    reviewed_at TIMESTAMP NULL,
    reviewer_comments VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL
);

CREATE TABLE payslips (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    pay_period VARCHAR(7) NOT NULL,
    basic_salary DECIMAL(15,2) NOT NULL,
    allowances DECIMAL(15,2) NOT NULL,
    deductions DECIMAL(15,2) NOT NULL,
    net_salary DECIMAL(15,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    generated_at TIMESTAMP NOT NULL,
    storage_path VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    CONSTRAINT uk_payslip_employee_period UNIQUE (employee_id, pay_period)
);

CREATE TABLE work_logs (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    work_date DATE NOT NULL,
    planned_tasks VARCHAR(1000),
    completed_tasks VARCHAR(1000) NOT NULL,
    blockers VARCHAR(1000),
    status VARCHAR(30) NOT NULL,
    manager_remarks VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    CONSTRAINT uk_worklog_employee_date UNIQUE (employee_id, work_date)
);

ALTER TABLE departments ADD CONSTRAINT fk_department_manager FOREIGN KEY (manager_employee_id) REFERENCES employees(id);
ALTER TABLE employees ADD CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES departments(id);
ALTER TABLE employees ADD CONSTRAINT fk_employee_manager FOREIGN KEY (manager_id) REFERENCES employees(id);
ALTER TABLE user_accounts ADD CONSTRAINT fk_user_account_employee FOREIGN KEY (employee_id) REFERENCES employees(id);
ALTER TABLE user_account_roles ADD CONSTRAINT fk_user_role_account FOREIGN KEY (user_account_id) REFERENCES user_accounts(id);
ALTER TABLE user_account_roles ADD CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES roles(id);
ALTER TABLE user_sessions ADD CONSTRAINT fk_user_session_account FOREIGN KEY (user_account_id) REFERENCES user_accounts(id);
ALTER TABLE attendance ADD CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id) REFERENCES employees(id);
ALTER TABLE leave_requests ADD CONSTRAINT fk_leave_employee FOREIGN KEY (employee_id) REFERENCES employees(id);
ALTER TABLE leave_requests ADD CONSTRAINT fk_leave_reviewer FOREIGN KEY (reviewed_by) REFERENCES user_accounts(id);
ALTER TABLE payslips ADD CONSTRAINT fk_payslip_employee FOREIGN KEY (employee_id) REFERENCES employees(id);
ALTER TABLE work_logs ADD CONSTRAINT fk_work_log_employee FOREIGN KEY (employee_id) REFERENCES employees(id);
