package co.com.prueba.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.com.prueba.api.dtos.response.ResponseError;
import co.com.prueba.usecase.exceptions.NotValidFieldException;
import co.com.prueba.usecase.exceptions.NotFoundException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotValidFieldException.class)
    public Mono<ResponseEntity<ResponseError>> handleNotValidFieldException(NotValidFieldException ex) {
        ResponseError body = ResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .detail(ex.getMessage())
                .build();
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ResponseError>> handleNotFoundException(NotFoundException ex) {
        ResponseError body = ResponseError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .detail(ex.getMessage())
                .build();
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(body));
    }

    @ExceptionHandler(IllegalStateException.class)
    public Mono<ResponseEntity<ResponseError>> handleIllegalStateException(IllegalStateException ex) {
        ResponseError body = ResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .detail(ex.getMessage())
                .build();
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ResponseError>> handleGeneralException(Exception ex) {
        ResponseError body = ResponseError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .detail(ex.getMessage())
                .build();
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }
}