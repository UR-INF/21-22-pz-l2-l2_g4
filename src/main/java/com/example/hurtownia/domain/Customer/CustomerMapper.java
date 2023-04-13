package com.example.hurtownia.domain.customer;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO mapToDto(Customer customer);
}
