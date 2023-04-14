package com.example.hurtownia.domain.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String surname;
    private String pesel;
    private String phoneNumber;
    private String email;
    private String place;
    private String street;
    private Integer apartmentNumber;
    private Integer buildingNumber;
}
