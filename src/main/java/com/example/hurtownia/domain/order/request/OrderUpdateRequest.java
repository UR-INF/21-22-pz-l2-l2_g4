package com.example.hurtownia.domain.order.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Reprezentuje update request dla obiektu order.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderUpdateRequest {
    private Long id;
    private Long customerId;
    private String date;
    private Double discount;
    private String state;
    private Double value;
}
