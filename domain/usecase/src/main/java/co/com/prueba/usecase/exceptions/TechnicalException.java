package co.com.prueba.usecase.exceptions;

public class TechnicalException extends RuntimeException {
    public TechnicalException(String message) {
        super(message);
    }
}