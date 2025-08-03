package com.test.client.service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ClientNotFoundExceptionTest {

    private static final String ERROR_MESSAGE = "Cliente no encontrado";
    private static final Throwable CAUSE = new RuntimeException("Causa original");

    @Test
    public void constructorWithMessage_ShouldSetMessage() {
        ClientNotFoundException exception = new ClientNotFoundException(ERROR_MESSAGE);
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void constructorWithMessageAndCause_ShouldSetBoth() {
        ClientNotFoundException exception = new ClientNotFoundException(ERROR_MESSAGE, CAUSE);
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }
}
