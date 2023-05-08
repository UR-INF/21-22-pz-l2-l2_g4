package com.example.hurtownia.domain.customer;

import com.example.hurtownia.domain.customer.request.CustomerCreateRequest;
import com.example.hurtownia.domain.customer.request.CustomerUpdateRequest;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    static Customer customer;
    static CustomerDTO customerDTO;
    static List<Customer> listOfCustomer;
    static List<CustomerDTO> listOfCustomerDTO;
    static CustomerCreateRequest customerCreateRequest;
    static CustomerUpdateRequest customerUpdateRequest;
    @Mock
    CustomerMapper customerMapper;
    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    @Spy
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();

        customerDTO = CustomerDTO.builder()
                .id(1L)
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();

        customerCreateRequest = CustomerCreateRequest.builder()
                .name("Customer")
                .surname("Surname")
                .pesel("21973812L")
                .email("email@com.pl")
                .place("Place")
                .phoneNumber("32873782")
                .apartmentNumber(1)
                .buildingNumber(1)
                .street("Street")
                .build();

        customerUpdateRequest = CustomerUpdateRequest.builder()
                .id(2L)
                .name("Customer2")
                .surname("Surname2")
                .pesel("31973812L")
                .email("email2@com.pl")
                .place("Place2")
                .phoneNumber("12873782")
                .apartmentNumber(2)
                .buildingNumber(2)
                .street("Street2")
                .build();

        listOfCustomer = List.of(customer);
        listOfCustomerDTO = List.of(customerDTO);
    }

    @Test
    void findAll() {
        when(customerRepository.findAll()).thenReturn(listOfCustomer);
        when(customerMapper.mapToDto(listOfCustomer)).thenReturn(listOfCustomerDTO);
        assertThat(customerService.findAll()).isEqualTo(listOfCustomerDTO);
    }

    @Test
    void findById() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> customerService.findById(any())).isInstanceOf(ObjectNotFoundException.class);

        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        assertThat(customerService.findById(any())).isEqualTo(customer);
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
        when(customerRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(customerService.create(customerCreateRequest))
                .satisfies(newCustomer -> {
                    assertThat(newCustomer.getName()).isEqualTo(customerCreateRequest.getName());
                    assertThat(newCustomer.getSurname()).isEqualTo(customerCreateRequest.getSurname());
                    assertThat(newCustomer.getPesel()).isEqualTo(customerCreateRequest.getPesel());
                    assertThat(newCustomer.getPhoneNumber()).isEqualTo(customerCreateRequest.getPhoneNumber());
                    assertThat(newCustomer.getEmail()).isEqualTo(customerCreateRequest.getEmail());
                    assertThat(newCustomer.getPlace()).isEqualTo(customerCreateRequest.getPlace());
                    assertThat(newCustomer.getStreet()).isEqualTo(customerCreateRequest.getStreet());
                    assertThat(newCustomer.getApartmentNumber()).isEqualTo(customerCreateRequest.getApartmentNumber());
                    assertThat(newCustomer.getBuildingNumber()).isEqualTo(customerCreateRequest.getBuildingNumber());
                });
    }

    @Test
    void update() {
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        when(customerRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(customerService.update(customerUpdateRequest))
                .satisfies(newCustomer -> {
                    assertThat(newCustomer.getName()).isEqualTo(customerUpdateRequest.getName());
                    assertThat(newCustomer.getSurname()).isEqualTo(customerUpdateRequest.getSurname());
                    assertThat(newCustomer.getPesel()).isEqualTo(customerUpdateRequest.getPesel());
                    assertThat(newCustomer.getPhoneNumber()).isEqualTo(customerUpdateRequest.getPhoneNumber());
                    assertThat(newCustomer.getEmail()).isEqualTo(customerUpdateRequest.getEmail());
                    assertThat(newCustomer.getPlace()).isEqualTo(customerUpdateRequest.getPlace());
                    assertThat(newCustomer.getStreet()).isEqualTo(customerUpdateRequest.getStreet());
                    assertThat(newCustomer.getApartmentNumber()).isEqualTo(customerUpdateRequest.getApartmentNumber());
                    assertThat(newCustomer.getBuildingNumber()).isEqualTo(customerUpdateRequest.getBuildingNumber());
                });
    }
}