package com.example.hurtownia.domain.orderitem.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Reprezentuje create request dla obiektu orderItem.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderItemCreateRequest {
    private Long orderId;
    private Long productId;
    private Integer amount;
}
