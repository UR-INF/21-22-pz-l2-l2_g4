package com.example.hurtownia.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Reprezentuje dane niezbędne do wygenerowania raportu dostawy.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SupplyData {
    private String supplierName;
    private String productCode;
    private String productUnitOfMeasurement;
    private String amount;
}
