package com.example.hurtownia.domain.supplier;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SupplierCreateDTO {

    private String email;
    private String country;
    private String place;
    private String street;
    private String name;
    private String nip;
}
