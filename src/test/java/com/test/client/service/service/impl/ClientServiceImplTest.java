package com.test.client.service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.test.client.service.exception.ClientNotFoundException;
import com.test.client.service.model.Client;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ClientServiceImpl class.
 */
class ClientServiceImplTest {

    private ClientServiceImpl clientService;
    
    private static final String VALID_DOCUMENT_TYPE = "C";
    private static final String VALID_DOCUMENT_NUMBER = "23445322";

    @BeforeEach
    void setUp() {
        clientService = new ClientServiceImpl();
    }

    @Nested
    @DisplayName("Tests for getClientInfo method")
    class GetClientInfoTests {

        @Test
        @DisplayName("Should return client when valid document type and number are provided")
        void shouldReturnClientWhenValidDocumentTypeAndNumberAreProvided() {
            Client client = clientService.getClientInfo(VALID_DOCUMENT_TYPE, VALID_DOCUMENT_NUMBER);
            
            assertNotNull(client, "Client should not be null");
            assertEquals("Juan", client.getFirstName(), "First name should match");
            assertEquals("Carlos", client.getSecondName(), "Second name should match");
            assertEquals("Rodríguez", client.getFirstLastName(), "First last name should match");
            assertEquals("Gómez", client.getSecondLastName(), "Second last name should match");
            assertEquals("3102224455", client.getPhoneNumber(), "Phone number should match");
            assertEquals("Calle 123 # 45-67", client.getAddress(), "Address should match");
            assertEquals("Bogotá", client.getCity(), "City should match");
        }

        @Test
        @DisplayName("Should throw ClientNotFoundException when invalid document type is provided")
        void shouldThrowClientNotFoundExceptionWhenInvalidDocumentTypeIsProvided() {
            String invalidDocumentType = "X";
            
            ClientNotFoundException exception = assertThrows(
                ClientNotFoundException.class,
                () -> clientService.getClientInfo(invalidDocumentType, VALID_DOCUMENT_NUMBER),
                "ClientNotFoundException should be thrown for invalid document type"
            );
            
            String expectedMessage = "No se encontró un cliente con el tipo de documento " + invalidDocumentType + " y número " + VALID_DOCUMENT_NUMBER;
            assertTrue(exception.getMessage().contains(expectedMessage));
        }

        @Test
        @DisplayName("Should throw ClientNotFoundException when invalid document number is provided")
        void shouldThrowClientNotFoundExceptionWhenInvalidDocumentNumberIsProvided() {
            String invalidDocumentNumber = "99999999";
            
            ClientNotFoundException exception = assertThrows(
                ClientNotFoundException.class,
                () -> clientService.getClientInfo(VALID_DOCUMENT_TYPE, invalidDocumentNumber),
                "ClientNotFoundException should be thrown for invalid document number"
            );
            
            String expectedMessage = "No se encontró un cliente con el tipo de documento " + VALID_DOCUMENT_TYPE + " y número " + invalidDocumentNumber;
            assertTrue(exception.getMessage().contains(expectedMessage));
        }

        @ParameterizedTest(name = "Document type: {0}, Document number: {1}")
        @MethodSource("provideInvalidDocuments")
        @DisplayName("Should throw ClientNotFoundException for various invalid document combinations")
        void shouldThrowClientNotFoundExceptionForVariousInvalidDocumentCombinations(
                String documentType, String documentNumber) {
            
            assertThrows(
                ClientNotFoundException.class,
                () -> clientService.getClientInfo(documentType, documentNumber),
                "ClientNotFoundException should be thrown for invalid document combinations"
            );
        }
        
        static Stream<Arguments> provideInvalidDocuments() {
            return Stream.of(
                Arguments.of("X", "23445322"),
                Arguments.of("C", "99999999"),
                Arguments.of("X", "99999999"),
                Arguments.of(null, VALID_DOCUMENT_NUMBER),
                Arguments.of(VALID_DOCUMENT_TYPE, null),
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of(VALID_DOCUMENT_TYPE, "")
            );
        }
    }
}
