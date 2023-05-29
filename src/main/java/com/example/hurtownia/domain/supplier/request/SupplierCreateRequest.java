package com.example.hurtownia.domain.supplier.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Reprezentuje create request dla obiektu supplier.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class SupplierCreateRequest {
    private String email;
    private String country;
    private String place;
    private String street;
    private String name;
    private String nip;


}
