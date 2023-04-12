package com.example.hurtownia.domain.customer;

import org.mapstruct.Mapper;

@Mapper
public abstract class CustomerMapper {

    public abstract CustomerDTO mapToDto(Customer customer);

    public abstract Customer mapToEntity(CustomerCreateRequest customerCreateRequest);

    public abstract Customer mapToEntity(CustomerUpdateRequest customerUpdateRequest);
}
