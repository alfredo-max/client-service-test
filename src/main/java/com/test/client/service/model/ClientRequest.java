package com.test.client.service.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Model class for Client request parameters
 */
public class ClientRequest {

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(regexp = "^[CP]$", message = "El tipo de documento solo puede ser C (cédula) o P (pasaporte)")
    private String documentType;

    @NotBlank(message = "El número de documento es obligatorio")
    private String documentNumber;

    public ClientRequest() {
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
