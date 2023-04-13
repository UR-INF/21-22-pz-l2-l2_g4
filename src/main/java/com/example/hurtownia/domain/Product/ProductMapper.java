package com.example.hurtownia.domain.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supply", constant = "false")
    ProductDTO mapToDto(Product product);
}
