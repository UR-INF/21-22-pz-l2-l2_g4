package com.example.hurtownia.domain.customer;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Obiekt DTO dla obiektu customer.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class CustomerDTO {
    private Long id;
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
