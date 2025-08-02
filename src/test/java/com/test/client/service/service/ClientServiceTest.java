package com.test.client.service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.client.service.exception.ClientNotFoundException;
import com.test.client.service.model.Client;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;
    
    @Test
    void getClientInfo_WithValidData_ShouldReturnClient() {
        // Arrange - Valid client data as per requirements
        String documentType = "C";
        String documentNumber = "23445322";
        
        // Act
        Client result = clientService.getClientInfo(documentType, documentNumber);
        
        // Assert
        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
        assertEquals("Carlos", result.getSecondName());
        assertEquals("Rodríguez", result.getFirstLastName());
        assertEquals("Gómez", result.getSecondLastName());
        assertEquals("3102224455", result.getPhoneNumber());
        assertEquals("Calle 123 # 45-67", result.getAddress());
        assertEquals("Bogotá", result.getCity());
    }
    
    @Test
    void getClientInfo_WithInvalidDocumentType_ShouldThrowException() {
        // Arrange
        String documentType = "C"; // Valid type
        String documentNumber = "99999999"; // Invalid number
        
        // Act & Assert
        ClientNotFoundException exception = assertThrows(
            ClientNotFoundException.class,
            () -> clientService.getClientInfo(documentType, documentNumber)
        );
        
        // Verify exception message
        assertEquals("No se encontró un cliente con el tipo de documento C y número 99999999", 
                exception.getMessage());
    }
    
    @Test
    void getClientInfo_WithInvalidDocumentNumber_ShouldThrowException() {
        // Arrange
        String documentType = "P"; // Valid type but not the one in our mock data
        String documentNumber = "23445322"; // Valid number but needs to match with C type
        
        // Act & Assert
        ClientNotFoundException exception = assertThrows(
            ClientNotFoundException.class,
            () -> clientService.getClientInfo(documentType, documentNumber)
        );
        
        // Verify exception message
        assertEquals("No se encontró un cliente con el tipo de documento P y número 23445322", 
                exception.getMessage());
    }
}
