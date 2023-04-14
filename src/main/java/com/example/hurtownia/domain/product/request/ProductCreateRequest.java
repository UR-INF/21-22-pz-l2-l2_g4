package com.example.hurtownia.domain.product.request;

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
public class ProductCreateRequest {

    private Long supplierId;
    private String name;
    private String unitOfMeasurement;
    private Double price;
    private String country;
    private String code;
    private String color;
    private Integer number;
    private Integer maxNumber;
}
