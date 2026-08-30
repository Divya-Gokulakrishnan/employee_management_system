package com.example.employee_management_system.core.auth.repository;

import com.example.employee_management_system.core.auth.entity.UserSession;
import com.example.employee_management_system.core.auth.enums.SessionStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByTokenAndStatus(String token, SessionStatus status);
}
