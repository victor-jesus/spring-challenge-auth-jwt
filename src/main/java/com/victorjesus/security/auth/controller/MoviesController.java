package com.victorjesus.security.auth.controller;

import com.victorjesus.security.auth.config.SecurityConfiguration;
import com.victorjesus.security.auth.domain.exception.MovieNotFoundException;
import com.victorjesus.security.auth.domain.movies.Genre;
import com.victorjesus.security.auth.domain.movies.Movie;
import com.victorjesus.security.auth.dto.movies.MoviePostRequest;
import com.victorjesus.security.auth.dto.movies.MovieResponse;
import com.victorjesus.security.auth.repository.MovieRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
@Tag(name = "movies", description = "Controller to get and post movies")
public class MoviesController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    @Operation(summary = "Get all movies", description = "Method to get a list of all movies")
    @ApiResponse(responseCode = "200", description = "Get movies with success!")
    @ApiResponse(responseCode = "400", description = "All fields must be filled.")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<List<Movie>> getMovies(){
        List<Movie> movies = movieRepository.findAll();

        return ResponseEntity.ok(movies);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Post a movie", description = "Method to post a movie")
    @ApiResponse(responseCode = "204", description = "Movie posted with success")
    @ApiResponse(responseCode = "400", description = "Fields must be filled.")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<?> postMovie(@RequestBody @Valid MoviePostRequest request){
        var movie = new Movie(request);

        movieRepository.save(movie);

        return ResponseEntity.noContent() .build();
    }

    @GetMapping("/search")
    @Operation(summary = "List movies by name", description = "Method to list movies by name")
    @ApiResponse(responseCode = "200", description = "Movies listed with success!")
    @ApiResponse(responseCode = "404", description = "Movies not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public List<ResponseEntity<MovieResponse>> listMoviesByName(@RequestParam String name) {
        List<Movie> movie = movieRepository.findByNameContainsIgnoreCase(name);

        if (movie.isEmpty()) {
            throw new MovieNotFoundException("Movies not found.");
        }

        return movie.stream()
                .map(m -> ResponseEntity.ok(new MovieResponse(m)))
                .toList();
    }

    @GetMapping("/search/director")
    @Operation(summary = "List movies by director name", description = "Method to list movies by director name")
    @ApiResponse(responseCode = "200", description = "Movies listed with success!")
    @ApiResponse(responseCode = "404", description = "Movies not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public List<ResponseEntity<MovieResponse>> listMoviesByDirectorName(@RequestParam String name){
        List<Movie> movies = movieRepository.findByDirectorNameContainsIgnoreCase(name);

        if(movies.isEmpty()) throw new MovieNotFoundException("Movies not found.");

        return movies.stream()
                .map(m -> ResponseEntity.ok(new MovieResponse(m)))
                .toList();
    }

    @GetMapping("/search/genre")
    @Operation(summary = "List movies by genre", description = "Method to list movies by genre")
    @ApiResponse(responseCode = "200", description = "Movies listed with success!")
    @ApiResponse(responseCode = "404", description = "Movies not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public List<ResponseEntity<MovieResponse>> listMoviesByDirectorName(@RequestParam Genre genre){
        List<Movie> movies = movieRepository.findByGenre(genre);

        if(movies.isEmpty()) throw new MovieNotFoundException("Movies not found.");

        return movies.stream()
                .map(m -> ResponseEntity.ok(new MovieResponse(m)))
                .toList();
    }
}
