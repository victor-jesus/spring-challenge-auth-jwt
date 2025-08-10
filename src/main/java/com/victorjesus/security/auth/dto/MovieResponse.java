package com.victorjesus.security.auth.dto;

import com.victorjesus.security.auth.domain.movies.Genre;

public record MovieResponse(
        String name,
        Integer durationInMinutes,
        Genre genre,
        String directorName
) {
}
