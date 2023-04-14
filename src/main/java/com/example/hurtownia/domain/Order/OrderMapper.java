package com.example.hurtownia.domain.Order;

import com.example.hurtownia.domain.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
//@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "customerDTO", source = "customer")
    OrderDTO mapToDto(Order order);
    List<OrderDTO> mapToDto(List<Order> orders);
}
