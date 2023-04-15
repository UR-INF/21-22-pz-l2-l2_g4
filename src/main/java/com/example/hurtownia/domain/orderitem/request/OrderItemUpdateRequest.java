package com.example.hurtownia.domain.orderitem.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderItemUpdateRequest {

    private Long id;
    private Long orderId;
    private Long productId;
    private Integer amount;
    private Double itemPrice;
    private Double pricePerUnit;
}