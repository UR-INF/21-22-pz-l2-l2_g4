package com.example.hurtownia.domain.supplier;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Obiekt DTO dla obiektu product.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SupplierDTO {
    private Long id;
    private String email;
    private String country;
    private String place;
    private String street;
    private String name;
    private String nip;
}
