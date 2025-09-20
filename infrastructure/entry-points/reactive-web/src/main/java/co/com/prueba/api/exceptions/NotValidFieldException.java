package co.com.prueba.api.exceptions;

public class NotValidFieldException extends RuntimeException {
    public NotValidFieldException(String message) {
        super(message);
    }
}