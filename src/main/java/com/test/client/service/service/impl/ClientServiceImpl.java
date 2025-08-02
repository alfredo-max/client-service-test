package com.test.client.service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.test.client.service.exception.ClientNotFoundException;
import com.test.client.service.model.Client;
import com.test.client.service.service.ClientService;


@Service
public class ClientServiceImpl implements ClientService {
    
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    
    private static final String VALID_DOCUMENT_TYPE = "C";
    private static final String VALID_DOCUMENT_NUMBER = "23445322";
    
    @Override
    public Client getClientInfo(String documentType, String documentNumber) {
        logger.info("Retrieving client info for document type: {} and number: {}", documentType, documentNumber);
        
        if (VALID_DOCUMENT_TYPE.equals(documentType) && VALID_DOCUMENT_NUMBER.equals(documentNumber)) {
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
        
        logger.warn("Client not found for document type: {} and number: {}", documentType, documentNumber);
        throw new ClientNotFoundException("No se encontró un cliente con el tipo de documento " 
                + documentType + " y número " + documentNumber);
    }
}
