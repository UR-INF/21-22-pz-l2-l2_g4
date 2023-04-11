package com.example.hurtownia.domain.supplier;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper pomiędzy Supplier i jego DTO.
 */
@Component
public class SupplierMapper {

    /**
     * Konwerter z Supplier na SupplierTableViewDTO.
     */
    Converter<Supplier, SupplierTableViewDTO> supplierToSupplierTableViewDTOConverter = context -> {
        SupplierTableViewDTO supplierTableViewDTO = new SupplierTableViewDTO();
        supplierTableViewDTO.setId(context.getSource().getId());
        supplierTableViewDTO.setEmail(context.getSource().getEmail());
        supplierTableViewDTO.setCountry(context.getSource().getCountry());
        supplierTableViewDTO.setPlace(context.getSource().getPlace());
        supplierTableViewDTO.setStreet(context.getSource().getStreet());
        supplierTableViewDTO.setName(context.getSource().getName());
        supplierTableViewDTO.setNip(context.getSource().getNip());
        return supplierTableViewDTO;
    };

    /**
     * Konwerter z SupplierTableViewDTO na Supplier.
     */
    Converter<SupplierTableViewDTO, Supplier> supplierTableViewDTOToSupplierConverter = context -> {
        Supplier supplier = new Supplier();
        supplier.setId(context.getSource().getId());
        supplier.setEmail(context.getSource().getEmail());
        supplier.setCountry(context.getSource().getCountry());
        supplier.setPlace(context.getSource().getPlace());
        supplier.setStreet(context.getSource().getStreet());
        supplier.setName(context.getSource().getName());
        supplier.setNip(context.getSource().getNip());
        return supplier;
    };

    /**
     * Konwerter z SupplierCreateDTO na Supplier.
     */
    Converter<SupplierCreateDTO, Supplier> supplierCreateDTOToSupplierConverter = context -> {
        Supplier supplier = new Supplier();
        supplier.setEmail(context.getSource().getEmail());
        supplier.setCountry(context.getSource().getCountry());
        supplier.setPlace(context.getSource().getPlace());
        supplier.setStreet(context.getSource().getStreet());
        supplier.setName(context.getSource().getName());
        supplier.setNip(context.getSource().getNip());
        return supplier;
    };
    private ModelMapper modelMapper;

    @Autowired
    public SupplierMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Supplier.class, SupplierTableViewDTO.class).setConverter(supplierToSupplierTableViewDTOConverter);
        modelMapper.createTypeMap(SupplierTableViewDTO.class, Supplier.class).setConverter(supplierTableViewDTOToSupplierConverter);
        modelMapper.createTypeMap(SupplierCreateDTO.class, Supplier.class).setConverter(supplierCreateDTOToSupplierConverter);
    }

    /**
     * Mapuje Supplier na SupplierTableViewDTO.
     *
     * @param supplier
     * @return obiekt SupplierTableViewDTO
     */
    public SupplierTableViewDTO toDTO(Supplier supplier) {
        return Objects.isNull(supplier) ? null : modelMapper.map(supplier, SupplierTableViewDTO.class);
    }

    /**
     * Mapuje SupplierTableViewDTO na Supplier.
     *
     * @param supplierTableViewDTO
     * @return obiekt Supplier
     */
    public Supplier toEntity(SupplierTableViewDTO supplierTableViewDTO) {
        return Objects.isNull(supplierTableViewDTO) ? null : modelMapper.map(supplierTableViewDTO, Supplier.class);
    }

    /**
     * Mapuje SupplierCreateDTO na Supplier.
     *
     * @param supplierCreateDTO
     * @return obiekt Supplier
     */
    public Supplier toEntity(SupplierCreateDTO supplierCreateDTO) {
        return Objects.isNull(supplierCreateDTO) ? null : modelMapper.map(supplierCreateDTO, Supplier.class);
    }
}
