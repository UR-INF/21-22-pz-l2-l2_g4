package com.example.hurtownia.domain.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    @Mapping(target = "customerId", source = "order.customer.id")
    OrderDTO mapToDto(Order order, double value);
}
