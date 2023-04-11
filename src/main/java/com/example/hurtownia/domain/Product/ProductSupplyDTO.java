package com.example.hurtownia.domain.product;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProductSupplyDTO {

    private String supplierName;
    private String unitOfMeasurement;
    private String code;
    private Integer amount;
}
