package com.example.hurtownia.domain.supplier;

import org.mapstruct.Mapper;

@Mapper
public abstract class SupplierMapper {

    public abstract SupplierDTO mapToDto(Supplier supplier);

    public abstract Supplier mapToEntity(SupplierCreateRequest supplierCreateRequest);

    public abstract Supplier mapToEntity(SupplierUpdateRequest supplierUpdateRequest);
}
