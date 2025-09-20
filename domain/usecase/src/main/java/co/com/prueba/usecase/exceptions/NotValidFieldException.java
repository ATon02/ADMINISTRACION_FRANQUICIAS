package co.com.prueba.usecase.exceptions;

public class NotValidFieldException extends RuntimeException {
    public NotValidFieldException(String message) {
        super(message);
    }
}