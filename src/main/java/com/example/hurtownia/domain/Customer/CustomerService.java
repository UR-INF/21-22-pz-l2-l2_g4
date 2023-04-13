package com.example.hurtownia.domain.customer;

import com.example.hurtownia.domain.customer.request.CustomerCreateRequest;
import com.example.hurtownia.domain.customer.request.CustomerUpdateRequest;
import com.example.hurtownia.domain.order.Order;
import com.example.hurtownia.domain.order.OrderService;
import com.example.hurtownia.domain.orderitem.request.OrderItemCreateRequest;
import com.example.hurtownia.domain.product.Product;
import com.example.hurtownia.domain.product.ProductService;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Zawiera metody dla tabeli 'klient'.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerMapper mapper;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Pobiera wszystkich klientów.
     *
     * @return lista wszystkich klientów
     */
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(customer -> mapper.mapToDto(customer))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera klienta o podanym id.
     *
     * @param id identyfikator klienta
     * @return klient
     */
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono klienta"));
    }

    /**
     * Usuwa klienta.
     *
     * @param id identyfikator usuwanego klienta
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Long id) {
        try {
            customerRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego klienta.
     *
     * @param customerCreateRequest obiekt z danymi do utworzenia nowego klienta
     */
    public Customer save(CustomerCreateRequest customerCreateRequest) {
        Customer customer = Customer.builder()
                .name(customerCreateRequest.getName())
                .surname(customerCreateRequest.getSurname())
                .pesel(customerCreateRequest.getPesel())
                .phoneNumber(customerCreateRequest.getPhoneNumber())
                .email(customerCreateRequest.getEmail())
                .place(customerCreateRequest.getPlace())
                .street(customerCreateRequest.getStreet())
                .buildingNumber(customerCreateRequest.getBuildingNumber())
                .apartmentNumber(customerCreateRequest.getApartmentNumber())
                .build();
        return customerRepository.save(customer);
    }

    /**
     * Aktualizuje klienta.
     *
     * @param customerUpdateRequest aktualizowany klient
     */
    public Customer update(CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = Customer.builder()
                .name(customerUpdateRequest.getName())
                .surname(customerUpdateRequest.getSurname())
                .pesel(customerUpdateRequest.getPesel())
                .phoneNumber(customerUpdateRequest.getPhoneNumber())
                .email(customerUpdateRequest.getEmail())
                .place(customerUpdateRequest.getPlace())
                .street(customerUpdateRequest.getStreet())
                .buildingNumber(customerUpdateRequest.getBuildingNumber())
                .apartmentNumber(customerUpdateRequest.getApartmentNumber())
                .build();
        return customerRepository.save(customer);
    }
}
