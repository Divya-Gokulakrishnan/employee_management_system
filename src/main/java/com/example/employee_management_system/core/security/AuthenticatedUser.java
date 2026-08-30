package com.example.employee_management_system.core.security;

import com.example.employee_management_system.core.auth.entity.UserAccount;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record AuthenticatedUser(UserAccount userAccount) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAccount.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
            .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return userAccount.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userAccount.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userAccount.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userAccount.isActive();
    }

    @Override
    public boolean isEnabled() {
        return userAccount.isActive();
    }
}
