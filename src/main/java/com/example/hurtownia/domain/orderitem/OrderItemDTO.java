package com.example.hurtownia.domain.orderitem;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Obiekt DTO dla obiektu orderItem.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer amount;
    private Double itemPrice;
    private Double pricePerUnit;
}
