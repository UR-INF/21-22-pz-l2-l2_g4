package com.example.hurtownia.domain.product;

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
public class ProductDTO {
    private Long id;
    private Long supplierId;
    private String name;
    private String unitOfMeasurement;
    private Double price;
    private String country;
    private String code;
    private String color;
    private Integer number;
    private Integer maxNumber;
    private Boolean supply;
}
