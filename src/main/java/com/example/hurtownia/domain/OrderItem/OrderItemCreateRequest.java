package com.example.hurtownia.domain.orderitem;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
