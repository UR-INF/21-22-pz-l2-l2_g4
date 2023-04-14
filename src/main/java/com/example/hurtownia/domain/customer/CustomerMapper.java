package com.example.hurtownia.domain.Customer;

import com.example.hurtownia.domain.customer.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO mapToDto(Customer customer);
    List<CustomerDTO> mapToDto(List<Customer> customers);
}
