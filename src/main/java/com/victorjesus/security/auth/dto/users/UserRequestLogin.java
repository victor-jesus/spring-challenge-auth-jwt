package com.victorjesus.security.auth.dto.users;

import jakarta.validation.constraints.NotBlank;

public record UserRequestLogin(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
