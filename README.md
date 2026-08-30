# Employee Management System

Production-style Spring Boot backend for employee administration, department management, attendance, leave workflow, payslips, daily work status, authentication, login/logout session tracking, and role-based access control.

## Modules

- `entity`: JPA domain entities for employees, departments, roles, sessions, attendance, leave requests, payslips, and work logs
- `enums`: domain enums for status, leave types, attendance states, session states, and roles
- `model`: request/response DTOs grouped by feature
- `controller`: REST endpoints under `/api/v1`
- `service`: business workflows and access rules
- `repository`: Spring Data repositories
- `mapper`: entity-to-response mappers
- `validation`: custom request validation
- `specification`: dynamic filtering for search/list endpoints
- `handler`: centralized API exception handling
- `config` and `security`: auditing, session-token security, seeded admin setup
- `db/changelog`: Liquibase changelogs for schema and seed data

## Features Implemented

- Employee CRUD with personal, professional, bank, tax, and salary details
- Department CRUD and employee-to-department assignment
- User account creation per employee with role assignment
- Login, logout, current-user lookup, and database-backed bearer sessions
- Role-based endpoint protection for admin, HR, manager, employee, and finance access
- Attendance check-in, check-out, and filtered listing
- Leave request creation plus approval/rejection flow
- Payslip creation and tracking with generated net salary
- Daily work log / done-today status tracking

## Default Access

- A startup initializer creates a default admin user if it does not already exist.
- Username: `admin`
- Password: `Admin@123`

## Local Run

1. Create MySQL database `employee_management_system`
2. Update `src/main/resources/application.properties` if your DB credentials differ
3. Run `./mvnw spring-boot:run`

## Main APIs

- `POST /api/v1/auth/login`
- `POST /api/v1/auth/logout`
- `GET /api/v1/auth/me`
- `GET|POST|PUT|DELETE /api/v1/employees`
- `PUT /api/v1/employees/{id}/department/{departmentId}`
- `GET|POST|PUT|DELETE /api/v1/departments`
- `GET /api/v1/roles`
- `POST /api/v1/roles/assign`
- `POST /api/v1/attendance/check-in`
- `PUT /api/v1/attendance/{attendanceId}/check-out`
- `GET /api/v1/attendance`
- `POST /api/v1/leaves`
- `PUT /api/v1/leaves/{leaveId}/review`
- `GET /api/v1/leaves`
- `POST /api/v1/payslips`
- `GET /api/v1/payslips`
- `POST /api/v1/worklogs`
- `GET /api/v1/worklogs`
