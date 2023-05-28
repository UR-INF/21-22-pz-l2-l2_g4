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
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Pobiera wszystkich klientów.
     *
     * @return lista wszystkich klientów
     */
    public List<CustomerDTO> findAll() {
        return customerMapper.mapToDto(customerRepository.findAll());
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
    public Customer create(CustomerCreateRequest customerCreateRequest) {
        Customer customer = Customer.builder()
                .name(customerCreateRequest.getName())
                .surname(customerCreateRequest.getSurname())
                .pesel(customerCreateRequest.getPesel())
                .phoneNumber(customerCreateRequest.getPhoneNumber())
                .email(customerCreateRequest.getEmail())
                .place(customerCreateRequest.getPlace())
                .zipCode(customerCreateRequest.getZipCode())
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
        Customer customer = findById(customerUpdateRequest.getId());
        customer.setName(customerUpdateRequest.getName());
        customer.setSurname(customerUpdateRequest.getSurname());
        customer.setPesel(customerUpdateRequest.getPesel());
        customer.setPhoneNumber(customerUpdateRequest.getPhoneNumber());
        customer.setEmail(customerUpdateRequest.getEmail());
        customer.setPlace(customerUpdateRequest.getPlace());
        customer.setStreet(customerUpdateRequest.getStreet());
        customer.setZipCode(customerUpdateRequest.getZipCode());
        customer.setBuildingNumber(customerUpdateRequest.getBuildingNumber());
        customer.setApartmentNumber(customerUpdateRequest.getApartmentNumber());
        return customerRepository.save(customer);
    }
}
