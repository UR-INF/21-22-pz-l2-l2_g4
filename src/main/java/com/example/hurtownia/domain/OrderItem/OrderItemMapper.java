package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public abstract class OrderItemMapper {

    @Mapping(target = "orderId", source = "orderItem.order.id")
    @Mapping(target = "productId", source = "orderItem.product.id")
    @Mapping(target = "itemPrice", source = "orderItem", qualifiedByName = "calculateItemValue")
    public abstract OrderItemDTO mapToDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", source = "order")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "pricePerUnit", source = "product.price")
    public abstract OrderItem mapToEntity(OrderItemCreateRequest orderItemCreateRequest, Order order, Product product);

    @Mapping(target = "id", source = "orderItemUpdateRequest.id")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "pricePerUnit", source = "product.price")
    public abstract OrderItem mapToEntity(OrderItemUpdateRequest orderItemUpdateRequest, Order order, Product product);

    @Named("calculateItemValue")
    Double calculateItemValue(OrderItem orderItem) {
        return orderItem.getPricePerUnit() * orderItem.getAmount();
    }
}
