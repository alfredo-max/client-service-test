package com.test.client.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.test.client.service.exception.ClientNotFoundException;
import com.test.client.service.model.Client;

/**
 * Service class to retrieve client information
 */
@Service
public class ClientService {
    
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    
    // Valid client data as specified in the requirements
    private static final String VALID_DOCUMENT_TYPE = "C"; // Cédula de ciudadanía
    private static final String VALID_DOCUMENT_NUMBER = "23445322";
    
    /**
     * Retrieve client information by document type and number
     * 
     * @param documentType the type of document (C or P)
     * @param documentNumber the document number
     * @return Client object with the client information
     * @throws ClientNotFoundException if the client is not found
     */
    public Client getClientInfo(String documentType, String documentNumber) {
        logger.info("Retrieving client info for document type: {} and number: {}", documentType, documentNumber);
        
        // Check if this is the valid client as per requirements
        if (VALID_DOCUMENT_TYPE.equals(documentType) && VALID_DOCUMENT_NUMBER.equals(documentNumber)) {
            // Return mocked data for the valid client
            return new Client(
                "Juan", 
                "Carlos", 
                "Rodríguez", 
                "Gómez", 
                "3102224455", 
                "Calle 123 # 45-67", 
                "Bogotá"
            );
        }
        
        // If no client is found, throw exception
        logger.warn("Client not found for document type: {} and number: {}", documentType, documentNumber);
        throw new ClientNotFoundException("No se encontró un cliente con el tipo de documento " 
                + documentType + " y número " + documentNumber);
    }
}
