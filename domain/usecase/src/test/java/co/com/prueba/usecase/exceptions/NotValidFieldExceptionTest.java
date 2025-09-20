package co.com.prueba.usecase.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotValidFieldExceptionTest {

    @Test
    void constructor_WithMessage_ShouldCreateExceptionWithMessage() {
        String message = "Field is not valid";

        NotValidFieldException exception = new NotValidFieldException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithNullMessage_ShouldCreateExceptionWithNullMessage() {
        NotValidFieldException exception = new NotValidFieldException(null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithEmptyMessage_ShouldCreateExceptionWithEmptyMessage() {
        String message = "";

        NotValidFieldException exception = new NotValidFieldException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void exceptionIsInstanceOfRuntimeException() {
        NotValidFieldException exception = new NotValidFieldException("Test message");

        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void canBeThrown() {
        String message = "Test validation exception";

        Exception thrownException = assertThrows(NotValidFieldException.class, () -> {
            throw new NotValidFieldException(message);
        });

        assertEquals(message, thrownException.getMessage());
    }

    @Test
    void differentInstancesWithSameMessageAreNotEqual() {
        String message = "Same message";
        NotValidFieldException exception1 = new NotValidFieldException(message);
        NotValidFieldException exception2 = new NotValidFieldException(message);

        assertNotEquals(exception1, exception2);
        assertEquals(exception1.getMessage(), exception2.getMessage());
    }
}
