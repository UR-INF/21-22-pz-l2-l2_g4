package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.customer.Customer;
import com.example.hurtownia.domain.orderitem.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class OrderMapper {

    @Mapping(target = "customerId", source = "order.customer.id")
    @Mapping(target = "value", expression = "java(calculateValue(orderItems))")
    public abstract OrderDTO mapToDto(Order order, List<OrderItem> orderItems);

    @Mapping(target = "customer", source = "customer")
    public abstract Order mapToEntity(OrderCreateRequest orderCreateRequest, Customer customer);

    @Mapping(target = "id", source = "orderUpdateRequest.id")
    public abstract Order mapToEntity(OrderUpdateRequest orderUpdateRequest, Customer customer);

    @Mapping(target = "name", source = "order.customer.name")
    @Mapping(target = "surname", source = "order.customer.surname")
    @Mapping(target = "value", expression = "java(String.valueOf(calculateValue(orderItems)))")
    @Mapping(target = "discount", expression = "java(DiscountConverter.fromNumericToPercentage(order.getDiscount()))")
    @Mapping(target = "valueAfterDiscount", expression = "java(calculateValueAfterDiscount(order, orderItems))")
    public abstract InvoiceData mapToInvoiceData(Order order, List<OrderItem> orderItems);

    String calculateValueAfterDiscount(Order order, List<OrderItem> orderItems) {
        double value = calculateValue(orderItems);
        double valueAfterDiscount = value - (value * order.getDiscount());
        return String.valueOf(valueAfterDiscount);
    }

    Double calculateValue(List<OrderItem> orderItems) {
        double value = 0.0;
        for (OrderItem orderItem : orderItems) value += orderItem.getPricePerUnit() * orderItem.getAmount();
        value = Math.round(value * 100.0) / 100.0;
        return value;
    }
}
