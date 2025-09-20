package co.com.prueba.usecase.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    void constructor_WithMessage_ShouldCreateExceptionWithMessage() {
        String message = "Resource not found";

        NotFoundException exception = new NotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithNullMessage_ShouldCreateExceptionWithNullMessage() {
        NotFoundException exception = new NotFoundException(null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithEmptyMessage_ShouldCreateExceptionWithEmptyMessage() {
        String message = "";

        NotFoundException exception = new NotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void exceptionIsInstanceOfRuntimeException() {
        NotFoundException exception = new NotFoundException("Test message");

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void canBeThrown() {
        String message = "Test exception";

        Exception thrownException = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException(message);
        });

        assertEquals(message, thrownException.getMessage());
    }
}
