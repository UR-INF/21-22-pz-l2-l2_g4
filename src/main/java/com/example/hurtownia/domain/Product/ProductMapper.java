package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.supplier.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class ProductMapper {

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supply", constant = "false")
    public abstract ProductDTO mapToDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "productCreateRequest.name")
    @Mapping(target = "country", source = "productCreateRequest.country")
    public abstract Product mapToEntity(ProductCreateRequest productCreateRequest, Supplier supplier);

    @Mapping(target = "id", source = "productUpdateRequest.id")
    @Mapping(target = "name", source = "productUpdateRequest.name")
    @Mapping(target = "country", source = "productUpdateRequest.country")
    public abstract Product mapToEntity(ProductUpdateRequest productUpdateRequest, Supplier supplier);

    @Mapping(target = "supplierName", source = "supplier.name")
    @Mapping(target = "productCode", source = "product.code")
    @Mapping(target = "productUnitOfMeasurement", source = "product.unitOfMeasurement")
    @Mapping(target = "amount", expression = "java(calculateAmount(product))")
    public abstract SupplyData mapToSupplyData(Product product, Supplier supplier);

    String calculateAmount(Product product) {
        return String.valueOf(product.getMaxNumber() - product.getNumber());
    }
}
