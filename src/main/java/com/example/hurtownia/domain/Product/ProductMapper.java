package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.supplier.SupplierService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper pomiędzy Product i jego DTO.
 */
@Component
public class ProductMapper {

    public SupplierService supplierService;

    /**
     * Konwerter z Product na ProductTableViewDTO.
     */
    Converter<Product, ProductTableViewDTO> productToProductTableViewDTOConverter = context -> {
        ProductTableViewDTO productTableViewDTO = new ProductTableViewDTO();
        productTableViewDTO.setId(context.getSource().getId());
        productTableViewDTO.setSupplierId(context.getSource().getSupplier().getId());
        productTableViewDTO.setName(context.getSource().getName());
        productTableViewDTO.setUnitOfMeasurement(context.getSource().getUnitOfMeasurement());
        productTableViewDTO.setPrice(context.getSource().getPrice());
        productTableViewDTO.setCountry(context.getSource().getCountry());
        productTableViewDTO.setCode(context.getSource().getCode());
        productTableViewDTO.setColor(context.getSource().getColor());
        productTableViewDTO.setNumber(context.getSource().getNumber());
        productTableViewDTO.setMaxNumber(context.getSource().getMaxNumber());
        productTableViewDTO.setSupply(Boolean.FALSE);
        return productTableViewDTO;
    };

    /**
     * Konwerter z ProductTableViewDTO na Product.
     */
    Converter<ProductTableViewDTO, Product> productTableViewDTOToProductConverter = context -> {
        Product product = new Product();
        product.setId(context.getSource().getId());
        product.setSupplier(supplierService.findById(context.getSource().getSupplierId()));
        product.setName(context.getSource().getName());
        product.setUnitOfMeasurement(context.getSource().getUnitOfMeasurement());
        product.setPrice(context.getSource().getPrice());
        product.setCountry(context.getSource().getCountry());
        product.setCode(context.getSource().getCode());
        product.setColor(context.getSource().getColor());
        product.setNumber(context.getSource().getNumber());
        product.setMaxNumber(context.getSource().getMaxNumber());
        return product;
    };

    /**
     * Konwerter z ProductCreateDTO na Product.
     */
    Converter<ProductCreateDTO, Product> productCreateDTOToProductConverter = context -> {
        Product product = new Product();
        product.setSupplier(supplierService.findById(context.getSource().getSupplierId()));
        product.setName(context.getSource().getName());
        product.setUnitOfMeasurement(context.getSource().getUnitOfMeasurement());
        product.setPrice(context.getSource().getPrice());
        product.setCountry(context.getSource().getCountry());
        product.setCode(context.getSource().getCode());
        product.setColor(context.getSource().getColor());
        product.setNumber(context.getSource().getNumber());
        product.setMaxNumber(context.getSource().getMaxNumber());
        return product;
    };

    /**
     * Konwerter z ProductTableViewDTO na ProductSupplyDTO.
     */
    Converter<ProductTableViewDTO, ProductSupplyDTO> productTableViewDTOToProductSupplyDTOConverter = context -> {
        ProductSupplyDTO productSupplyDTO = new ProductSupplyDTO();
        productSupplyDTO.setSupplierName(supplierService.findById(context.getSource().getSupplierId()).getName());
        productSupplyDTO.setCode(context.getSource().getCode());
        productSupplyDTO.setUnitOfMeasurement(context.getSource().getUnitOfMeasurement());
        productSupplyDTO.setAmount(context.getSource().getMaxNumber() - context.getSource().getNumber());
        return productSupplyDTO;
    };
    private ModelMapper modelMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper, SupplierService supplierService) {
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;

        modelMapper.createTypeMap(Product.class, ProductTableViewDTO.class).setConverter(productToProductTableViewDTOConverter);
        modelMapper.createTypeMap(ProductTableViewDTO.class, ProductSupplyDTO.class).setConverter(productTableViewDTOToProductSupplyDTOConverter);
        modelMapper.createTypeMap(ProductTableViewDTO.class, Product.class).setConverter(productTableViewDTOToProductConverter);
        modelMapper.createTypeMap(ProductCreateDTO.class, Product.class).setConverter(productCreateDTOToProductConverter);
    }

    /**
     * Mapuje Product na ProductTableViewDTO.
     *
     * @param product
     * @return obiekt ProductTableViewDTO
     */
    public ProductTableViewDTO toDTO(Product product) {
        return Objects.isNull(product) ? null : modelMapper.map(product, ProductTableViewDTO.class);
    }

    /**
     * Mapuje ProductTableViewDTO na ProductSupplyDTO.
     *
     * @param productTableViewDTO
     * @return obiekt ProductTableViewDTO
     */
    public ProductSupplyDTO toDTO(ProductTableViewDTO productTableViewDTO) {
        return Objects.isNull(productTableViewDTO) ? null : modelMapper.map(productTableViewDTO, ProductSupplyDTO.class);
    }

    /**
     * Mapuje ProductTableViewDTO na Product.
     *
     * @param productTableViewDTO
     * @return obiekt Product
     */
    public Product toEntity(ProductTableViewDTO productTableViewDTO) {
        return Objects.isNull(productTableViewDTO) ? null : modelMapper.map(productTableViewDTO, Product.class);
    }

    /**
     * Mapuje ProductCreateDTO na Product.
     *
     * @param productCreateDTO
     * @return obiekt Product
     */
    public Product toEntity(ProductCreateDTO productCreateDTO) {
        return Objects.isNull(productCreateDTO) ? null : modelMapper.map(productCreateDTO, Product.class);
    }
}