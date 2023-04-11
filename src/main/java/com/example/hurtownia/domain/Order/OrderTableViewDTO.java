package com.example.hurtownia.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * DTO dla widoku tabeli zamówień.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderTableViewDTO {

    private Long id;
    private Long customerId;
    private String date;
    private Double discount;
    private String state;
    private Double value;
}
