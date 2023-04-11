package com.example.hurtownia.domain.orderitem;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderItemCreateDTO {

    private Long orderid;
    private Long productId;
    private Integer amount;
}
