package com.test.client.service.service;

import com.test.client.service.model.Client;

public interface ClientService {

    Client getClientInfo(String documentType, String documentNumber);
}
