package com.example.hurtownia.domain.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * DTO dla tworzenia klienta.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class CustomerCreateDTO {

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
