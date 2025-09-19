package com.management.franchises.management.franchises.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.management.franchises.management.franchises.models.dtos.response.ResponseError;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseError> handleException(Exception ex) {
        return Mono.just(new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public Mono<ResponseError> handleException(IllegalStateException ex) {
        return Mono.just(new ResponseError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseError> handleNotFoundException(NotFoundException ex) {
        return Mono.just(new ResponseError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(NotValidFieldException.class)
    public Mono<ResponseError> handleNotValidFieldException(NotValidFieldException ex) {
        return Mono.just(new ResponseError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

}