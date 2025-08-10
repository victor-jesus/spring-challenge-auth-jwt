package com.victorjesus.security.auth.repository;

import com.victorjesus.security.auth.domain.movies.Genre;
import com.victorjesus.security.auth.domain.movies.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByNameContainsIgnoreCase(String name);

    List<Movie> findByDirectorNameContainsIgnoreCase(String directorName);

    List<Movie> findByGenre(Genre genre);
}
