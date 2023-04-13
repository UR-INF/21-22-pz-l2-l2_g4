package com.example.hurtownia.domain.supplier;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    SupplierDTO mapToDto(Supplier supplier);
}
