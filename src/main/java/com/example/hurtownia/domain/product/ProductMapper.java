package com.example.hurtownia.domain.product;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface ProductMapper {

    @IterableMapping(qualifiedByName = "mapToDto")
    @Named("mapListToDto")
    List<ProductDTO> mapListToDto(List<Product> products);

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supply", constant = "false")
    @Named("mapToDto")
    ProductDTO mapToDto(Product product);
}
