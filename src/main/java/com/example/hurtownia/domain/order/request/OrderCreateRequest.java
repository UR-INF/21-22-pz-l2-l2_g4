package com.example.hurtownia.domain.order.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Reprezentuje create request dla obiektu order.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderCreateRequest {
    private Long customerId;
    private String date;
    private Double discount;
    private String state;
}
