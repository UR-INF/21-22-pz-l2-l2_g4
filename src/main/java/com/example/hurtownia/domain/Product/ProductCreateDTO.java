package com.example.hurtownia.domain.product;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProductCreateDTO {

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
