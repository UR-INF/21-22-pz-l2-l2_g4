package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderItemData {
    private OrderItemDTO orderItem;
    private Product product;
}
