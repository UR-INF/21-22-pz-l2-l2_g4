package com.example.hurtownia.domain.customer.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class CustomerCreateRequest {

    private String name;
    private String surname;
    private String pesel;
    private String phoneNumber;
    private String email;
    private String place;
    private String street;
    private String zipCode;
    private Integer apartmentNumber;
    private Integer buildingNumber;
}
