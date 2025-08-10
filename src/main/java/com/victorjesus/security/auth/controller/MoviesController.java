package com.victorjesus.security.auth.controller;

import com.victorjesus.security.auth.domain.movies.Movie;
import com.victorjesus.security.auth.dto.MoviePostRequest;
import com.victorjesus.security.auth.repository.MovieRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MoviesController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(){
        List<Movie> movies = movieRepository.findAll();

        return ResponseEntity.ok(movies);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> postMovie(@RequestBody @Valid MoviePostRequest request){
        var movie = new Movie(request);

        movieRepository.save(movie);

        return ResponseEntity.ok().build();
    }
}
