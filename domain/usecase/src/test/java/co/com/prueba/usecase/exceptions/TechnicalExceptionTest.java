package co.com.prueba.usecase.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnicalExceptionTest {

    @Test
    void constructor_WithMessage_ShouldCreateExceptionWithMessage() {
        String message = "Field is not valid";

        TechnicalException exception = new TechnicalException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithNullMessage_ShouldCreateExceptionWithNullMessage() {
        TechnicalException exception = new TechnicalException(null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithEmptyMessage_ShouldCreateExceptionWithEmptyMessage() {
        String message = "";

        TechnicalException exception = new TechnicalException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void exceptionIsInstanceOfRuntimeException() {
        TechnicalException exception = new TechnicalException("Test message");

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void canBeThrown() {
        String message = "Test validation exception";

        Exception thrownException = assertThrows(TechnicalException.class, () -> {
            throw new TechnicalException(message);
        });

        assertEquals(message, thrownException.getMessage());
    }

    @Test
    void differentInstancesWithSameMessageAreNotEqual() {
        String message = "Same message";
        TechnicalException exception1 = new TechnicalException(message);
        TechnicalException exception2 = new TechnicalException(message);

        assertNotEquals(exception1, exception2);
        assertEquals(exception1.getMessage(), exception2.getMessage());
    }
}
