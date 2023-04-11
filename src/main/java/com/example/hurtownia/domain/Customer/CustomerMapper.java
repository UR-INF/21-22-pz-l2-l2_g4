package com.example.hurtownia.domain.customer;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper pomiędzy Customer i jego DTO.
 */
@Component
public class CustomerMapper {

    /**
     * Konwerter z Customer na CustomerTableViewDTO.
     */
    Converter<Customer, CustomerTableViewDTO> customerToCustomerTableViewDTOConverter = context -> {
        CustomerTableViewDTO customerTableViewDTO = new CustomerTableViewDTO();
        customerTableViewDTO.setId(context.getSource().getId());
        customerTableViewDTO.setName(context.getSource().getName());
        customerTableViewDTO.setSurname(context.getSource().getSurname());
        customerTableViewDTO.setPesel(context.getSource().getPesel());
        customerTableViewDTO.setPhoneNumber(context.getSource().getPhoneNumber());
        customerTableViewDTO.setEmail(context.getSource().getEmail());
        customerTableViewDTO.setPlace(context.getSource().getPlace());
        customerTableViewDTO.setStreet(context.getSource().getStreet());
        customerTableViewDTO.setApartmentNumber(context.getSource().getApartmentNumber());
        customerTableViewDTO.setBuildingNumber(context.getSource().getBuildingNumber());
        return customerTableViewDTO;
    };

    /**
     * Konwerter z CustomerTableViewDTO na Customer.
     */
    Converter<CustomerTableViewDTO, Customer> customerTableViewDTOToCustomerConverter = context -> {
        Customer customer = new Customer();
        customer.setId(context.getSource().getId());
        customer.setName(context.getSource().getName());
        customer.setSurname(context.getSource().getSurname());
        customer.setPesel(context.getSource().getPesel());
        customer.setPhoneNumber(context.getSource().getPhoneNumber());
        customer.setEmail(context.getSource().getEmail());
        customer.setPlace(context.getSource().getPlace());
        customer.setStreet(context.getSource().getStreet());
        customer.setApartmentNumber(context.getSource().getApartmentNumber());
        customer.setBuildingNumber(context.getSource().getBuildingNumber());
        return customer;
    };

    /**
     * Konwerter z CustomerCreateDTO na Customer.
     */
    Converter<CustomerCreateDTO, Customer> customerCreateDTOToCustomerConverter = context -> {
        Customer customer = new Customer();
        customer.setName(context.getSource().getName());
        customer.setSurname(context.getSource().getSurname());
        customer.setPesel(context.getSource().getPesel());
        customer.setPhoneNumber(context.getSource().getPhoneNumber());
        customer.setEmail(context.getSource().getEmail());
        customer.setPlace(context.getSource().getPlace());
        customer.setStreet(context.getSource().getStreet());
        customer.setApartmentNumber(Integer.valueOf(context.getSource().getApartmentNumber()));
        customer.setBuildingNumber(Integer.valueOf(context.getSource().getBuildingNumber()));
        return customer;
    };
    private ModelMapper modelMapper;

    @Autowired
    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(Customer.class, CustomerTableViewDTO.class).setConverter(customerToCustomerTableViewDTOConverter);
        modelMapper.createTypeMap(CustomerTableViewDTO.class, Customer.class).setConverter(customerTableViewDTOToCustomerConverter);
        modelMapper.createTypeMap(CustomerCreateDTO.class, Customer.class).setConverter(customerCreateDTOToCustomerConverter);
    }

    /**
     * Mapuje Customer na CustomerTableViewDTO.
     *
     * @param customer
     * @return obiekt CustomerTableViewDTO
     */
    public CustomerTableViewDTO toDTO(Customer customer) {
        return Objects.isNull(customer) ? null : modelMapper.map(customer, CustomerTableViewDTO.class);
    }

    /**
     * Mapuje CustomerCreateDTO na Customer.
     *
     * @param customerCreateDTO
     * @return obiekt Customer
     */
    public Customer toEntity(CustomerCreateDTO customerCreateDTO) {
        return Objects.isNull(customerCreateDTO) ? null : modelMapper.map(customerCreateDTO, Customer.class);
    }

    /**
     * Mapuje CustomerTableViewDTO na Customer.
     *
     * @param customerTableViewDTO
     * @return obiekt Customer
     */
    public Customer toEntity(CustomerTableViewDTO customerTableViewDTO) {
        return Objects.isNull(customerTableViewDTO) ? null : modelMapper.map(customerTableViewDTO, Customer.class);
    }
}
