package com.victorjesus.security.auth.dto.movies;

import com.victorjesus.security.auth.domain.movies.Genre;
import com.victorjesus.security.auth.domain.movies.Movie;

public record MovieResponse(
        String name,
        Integer durationInMinutes,
        Genre genre,
        String directorName
) {
    public MovieResponse(Movie movie) {
        this(movie.getName(), movie.getDurationInMinutes(), movie.getGenre(), movie.getDirectorName());

    }
}
