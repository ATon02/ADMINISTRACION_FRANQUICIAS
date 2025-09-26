package co.com.prueba.usecase.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Test
    void constructor_WithMessage_ShouldCreateExceptionWithMessage() {
        String message = "Resource not found";

        BusinessException exception = new BusinessException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithNullMessage_ShouldCreateExceptionWithNullMessage() {
        BusinessException exception = new BusinessException(null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithEmptyMessage_ShouldCreateExceptionWithEmptyMessage() {
        String message = "";

        BusinessException exception = new BusinessException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void exceptionIsInstanceOfRuntimeException() {
        BusinessException exception = new BusinessException("Test message");

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void canBeThrown() {
        String message = "Test exception";

        Exception thrownException = assertThrows(BusinessException.class, () -> {
            throw new BusinessException(message);
        });

        assertEquals(message, thrownException.getMessage());
    }
}
