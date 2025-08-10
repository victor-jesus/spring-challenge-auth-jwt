package com.victorjesus.security.auth.domain.exception;

public record ExceptionResponse(
        Integer code,
        String error,
        String message
) {

}
