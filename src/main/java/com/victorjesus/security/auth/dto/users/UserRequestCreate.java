package com.victorjesus.security.auth.dto.users;

import com.victorjesus.security.auth.domain.users.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestCreate(
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotNull
        UserRole role
) {
}
