package com.example.employee_management_system.core.auth.service;

import com.example.employee_management_system.core.config.SecurityProperties;
import com.example.employee_management_system.core.auth.entity.UserAccount;
import com.example.employee_management_system.core.auth.entity.UserSession;
import com.example.employee_management_system.core.auth.enums.SessionStatus;
import com.example.employee_management_system.core.handler.UnauthorizedException;
import com.example.employee_management_system.core.auth.mapper.UserAccountMapper;
import com.example.employee_management_system.core.auth.model.LoginRequest;
import com.example.employee_management_system.core.auth.model.LoginResponse;
import com.example.employee_management_system.core.auth.model.UserSummaryResponse;
import com.example.employee_management_system.core.auth.repository.UserAccountRepository;
import com.example.employee_management_system.core.auth.repository.UserSessionRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final UserSessionRepository userSessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;
    private final CurrentUserService currentUserService;
    private final UserAccountMapper userAccountMapper;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        UserAccount userAccount = userAccountRepository.findByUsername(request.username())
            .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        if (!userAccount.isActive() || !passwordEncoder.matches(request.password(), userAccount.getPasswordHash())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        LocalDateTime now = LocalDateTime.now();
        UserSession session = new UserSession();
        session.setToken(UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", ""));
        session.setStatus(SessionStatus.ACTIVE);
        session.setLoginAt(now);
        session.setExpiresAt(now.plusHours(securityProperties.sessionTokenValidityHours()));
        session.setUserAccount(userAccount);
        userSessionRepository.save(session);

        userAccount.setLastLoginAt(now);
        userAccountRepository.save(userAccount);

        return new LoginResponse(
            session.getToken(),
            userAccount.getUsername(),
            userAccount.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()),
            session.getLoginAt(),
            session.getExpiresAt()
        );
    }

    @Transactional
    public void logout(String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        UserSession session = userSessionRepository.findByTokenAndStatus(token, SessionStatus.ACTIVE)
            .orElseThrow(() -> new UnauthorizedException("Active session not found"));
        session.setStatus(SessionStatus.LOGGED_OUT);
        session.setLogoutAt(LocalDateTime.now());
        userSessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public UserSummaryResponse currentUser() {
        return userAccountMapper.toResponse(currentUserService.getCurrentUserAccount());
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Authorization token is missing");
        }
        return authorizationHeader.substring(7);
    }
}
