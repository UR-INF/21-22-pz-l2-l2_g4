package com.example.hurtownia.domain.customer;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerDTO mapToDto(Customer customer);

    List<CustomerDTO> mapToDto(List<Customer> customers);
}
