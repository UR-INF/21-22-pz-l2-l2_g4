package com.example.hurtownia.domain.supplier;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SupplierMapper {

    SupplierDTO mapToDto(Supplier supplier);

    List<SupplierDTO> mapListToDto(List<Supplier> suppliers);
}
