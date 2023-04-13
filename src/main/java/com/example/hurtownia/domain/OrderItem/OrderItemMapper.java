package com.example.hurtownia.domain.orderitem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "orderId", source = "orderItem.order.id")
    @Mapping(target = "productId", source = "orderItem.product.id")
    @Mapping(target = "itemPrice", source = "orderItem", qualifiedByName = "calculateItemValue")
    OrderItemDTO mapToDto(OrderItem orderItem);

    @Named("calculateItemValue")
    default double calculateItemValue(OrderItem orderItem) {
        return orderItem.getPricePerUnit() * orderItem.getAmount();
    }
}
