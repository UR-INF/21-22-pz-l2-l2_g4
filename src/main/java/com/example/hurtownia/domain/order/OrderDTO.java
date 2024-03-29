package com.example.hurtownia.domain.order;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Obiekt DTO dla obiektu order.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderDTO {
    private Long id;
    private Long customerId;
    private String date;
    private Double discount;
    private String state;
    private Double value;
}
