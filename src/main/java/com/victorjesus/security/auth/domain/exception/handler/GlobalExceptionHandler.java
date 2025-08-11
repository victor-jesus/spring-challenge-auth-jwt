package com.victorjesus.security.auth.domain.exception.handler;

import com.victorjesus.security.auth.domain.exception.ExceptionResponse;
import com.victorjesus.security.auth.domain.exception.MovieNotFoundException;
import com.victorjesus.security.auth.domain.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> throwsEntityNotFoundException(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(HttpStatus.NOT_FOUND.value(), "Error: ", "User not found."));
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ExceptionResponse> throwsMovieNotFoundException(MovieNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(HttpStatus.NOT_FOUND.value(), "Error: ", exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> throwsMovieNotFoundException(UserNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(HttpStatus.NOT_FOUND.value(), "Error: ", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionResponse>> throwsMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        var errorsField = exception.getFieldErrors();
        return ResponseEntity.badRequest()
                .body(errorsField.stream()
                        .map(e -> new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getField(), e.getDefaultMessage()))
                        .toList()
                );
    }

}
