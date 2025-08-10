package com.victorjesus.security.auth.controller;

import com.victorjesus.security.auth.config.SecurityConfiguration;
import com.victorjesus.security.auth.domain.movies.Movie;
import com.victorjesus.security.auth.dto.movies.MoviePostRequest;
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
    @ApiResponse(responseCode = "200", description = "Get movies with sucess!")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<List<Movie>> getMovies(){
        List<Movie> movies = movieRepository.findAll();

        return ResponseEntity.ok(movies);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Post a movie", description = "Method to post a movie")
    @ApiResponse(responseCode = "200", description = "Movie posted with sucess")
    public ResponseEntity<?> postMovie(@RequestBody @Valid MoviePostRequest request){
        var movie = new Movie(request);

        movieRepository.save(movie);

        return ResponseEntity.ok().build();
    }
}
