package com.example.hurtownia.domain.orderitem;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderItemTableViewDTO {

    private Long id;
    private Long orderid;
    private Long productId;
    private Integer amount;
    private Double itemPrice;
    private Double pricePerUnit;
}
