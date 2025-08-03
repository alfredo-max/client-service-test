package com.test.client.service.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Client {
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String phoneNumber;
    private String address;
    private String city;
}
