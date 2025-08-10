package com.victorjesus.security.auth.domain.exception.handler;

import com.victorjesus.security.auth.domain.exception.ExceptionResponse;
import com.victorjesus.security.auth.domain.exception.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ExceptionResponse> throwsMovieNotFoundException(MovieNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Error: ", exception.getMessage()));
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
