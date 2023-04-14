package com.example.hurtownia.domain.orderitem;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    @IterableMapping(qualifiedByName = "mapToDto")
    @Named("mapListToDto")
    List<OrderItemDTO> mapListToDto(List<OrderItem> orderItems);

    @Mapping(target = "orderId", source = "orderItem.order.id")
    @Mapping(target = "productId", source = "orderItem.product.id")
    @Mapping(target = "itemPrice", source = "orderItem", qualifiedByName = "calculateItemValue")
    @Named("mapToDto")
    OrderItemDTO mapToDto(OrderItem orderItem);

    @Named("calculateItemValue")
    default double calculateItemValue(OrderItem orderItem) {
        return orderItem.getPricePerUnit() * orderItem.getAmount();
    }
}
