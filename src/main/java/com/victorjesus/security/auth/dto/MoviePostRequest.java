package com.victorjesus.security.auth.dto;

import com.victorjesus.security.auth.domain.movies.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MoviePostRequest(
        @NotBlank
        String name,
        Integer durationInMinutes,
        @NotNull
        Genre genre,
        String directorName
) {
}
