package com.example.hurtownia.domain.supplier;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SupplierUpdateRequest {

    private Long id;
    private String email;
    private String country;
    private String place;
    private String street;
    private String name;
    private String nip;
}
