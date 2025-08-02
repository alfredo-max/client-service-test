package com.test.client.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.test.client.service.exception.ClientNotFoundException;
import com.test.client.service.model.Client;
import com.test.client.service.model.ClientRequest;
import com.test.client.service.model.ErrorResponse;
import com.test.client.service.service.ClientService;

/**
 * REST Controller for client information
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    
    @Autowired
    private ClientService clientService;
    
    /**
     * Get client information by document type and number
     * 
     * @param request the client request with document type and number
     * @param bindingResult validation result
     * @return ResponseEntity with client information or error response
     */
    @GetMapping("/info")
    public ResponseEntity<Client> getClientInfo(@Valid ClientRequest request, BindingResult bindingResult) {
        logger.info("Received request for client info with document type: {} and number: {}", 
                request.getDocumentType(), request.getDocumentNumber());
        
        // Validation is handled by @Valid and the Exception handler
        
        // Get client info from service
        Client client = clientService.getClientInfo(request.getDocumentType(), request.getDocumentNumber());
        
        // Return success response with client data
        return ResponseEntity.ok(client);
    }
    
    /**
     * Exception handler for validation errors (400 Bad Request)
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, 
            MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex, HttpServletRequest request) {
        logger.error("Validation error: {}", ex.getMessage());
        
        String errorMessage;
        if (ex instanceof MethodArgumentNotValidException) {
            // Extract validation error message
            MethodArgumentNotValidException validationEx = (MethodArgumentNotValidException) ex;
            errorMessage = validationEx.getBindingResult().getFieldError().getDefaultMessage();
        } else {
            errorMessage = "Error de validación en los parámetros de entrada";
        }
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessage,
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    /**
     * Exception handler for client not found (404 Not Found)
     */
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException ex, HttpServletRequest request) {
        logger.error("Client not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    /**
     * Exception handler for general server errors (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception ex, HttpServletRequest request) {
        logger.error("Server error: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Error interno del servidor",
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
