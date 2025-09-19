package com.management.franchises.management.franchises.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.management.franchises.management.franchises.models.dtos.response.ResponseError;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {


   @ExceptionHandler(NotValidFieldException.class)
    public Mono<ResponseEntity<ResponseError>> handleNotValidFieldException(NotValidFieldException ex) {
        ResponseError body = new ResponseError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ResponseError>> handleNotFoundException(NotFoundException ex) {
        ResponseError body = new ResponseError(HttpStatus.NOT_FOUND, ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ResponseError>> handleException(IllegalStateException ex) {
        ResponseError body = new ResponseError(HttpStatus.NOT_FOUND, ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(body));
    }

    @ExceptionHandler(IllegalStateException.class)
    public Mono<ResponseEntity<ResponseError>> handleException(Exception ex) {
        ResponseError body = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }


}