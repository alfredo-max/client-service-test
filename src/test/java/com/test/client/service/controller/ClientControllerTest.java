package com.test.client.service.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.test.client.service.exception.ClientNotFoundException;
import com.test.client.service.model.Client;
import com.test.client.service.service.ClientService;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;
    
    private Client mockClient;

    @BeforeEach
    void setUp() {
        // Set up mock client data
        mockClient = new Client(
            "Juan", 
            "Carlos", 
            "Rodríguez", 
            "Gómez", 
            "3102224455", 
            "Calle 123 # 45-67", 
            "Bogotá"
        );
    }

    @Test
    void getClientInfo_ValidRequest_ShouldReturnOk() throws Exception {
        // Arrange
        when(clientService.getClientInfo("C", "23445322")).thenReturn(mockClient);

        // Act & Assert - 200 OK
        mockMvc.perform(get("/api/clients/info")
                .param("documentType", "C")
                .param("documentNumber", "23445322"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.secondName").value("Carlos"))
                .andExpect(jsonPath("$.firstLastName").value("Rodríguez"))
                .andExpect(jsonPath("$.secondLastName").value("Gómez"))
                .andExpect(jsonPath("$.phoneNumber").value("3102224455"))
                .andExpect(jsonPath("$.address").value("Calle 123 # 45-67"))
                .andExpect(jsonPath("$.city").value("Bogotá"));
    }
    
    @Test
    void getClientInfo_InvalidDocumentType_ShouldReturnBadRequest() throws Exception {
        // Act & Assert - 400 Bad Request (invalid document type)
        mockMvc.perform(get("/api/clients/info")
                .param("documentType", "X") // Invalid type, only C or P allowed
                .param("documentNumber", "23445322"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void getClientInfo_MissingParameters_ShouldReturnBadRequest() throws Exception {
        // Act & Assert - 400 Bad Request (missing parameters)
        mockMvc.perform(get("/api/clients/info"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void getClientInfo_ClientNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(clientService.getClientInfo(anyString(), anyString()))
                .thenThrow(new ClientNotFoundException("No se encontró un cliente"));

        // Act & Assert - 404 Not Found
        mockMvc.perform(get("/api/clients/info")
                .param("documentType", "P")
                .param("documentNumber", "12345678"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void getClientInfo_ServerError_ShouldReturnInternalServerError() throws Exception {
        // Arrange
        when(clientService.getClientInfo(anyString(), anyString()))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert - 500 Internal Server Error
        mockMvc.perform(get("/api/clients/info")
                .param("documentType", "C")
                .param("documentNumber", "23445322"))
                .andExpect(status().isInternalServerError());
    }
}
