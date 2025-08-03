package com.test.client.service.controller;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.client.service.model.Client;
import com.test.client.service.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@Validated
public class ClientController {


    private final ClientService clientService;
    
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/info")
    public ResponseEntity<Client> getClientInfo(
            @RequestParam @NotBlank(message = "El tipo de documento es obligatorio")
            @Pattern(regexp = "^[CP]$", message = "El tipo de documento solo puede ser C (cédula) o P (pasaporte)")
            String documentType,

            @RequestParam @NotBlank(message = "El número de documento es obligatorio")
            String documentNumber) {

        Client client = clientService.getClientInfo(documentType, documentNumber);

        return ResponseEntity.ok(client);
    }
}
