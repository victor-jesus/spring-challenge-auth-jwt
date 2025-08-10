package com.victorjesus.security.auth.dto.users;

import com.victorjesus.security.auth.domain.users.User;
import com.victorjesus.security.auth.domain.users.UserRole;

public record UserResponseDTO(
        String id,
        String login,
        String password,
        UserRole role
) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
    }
}
