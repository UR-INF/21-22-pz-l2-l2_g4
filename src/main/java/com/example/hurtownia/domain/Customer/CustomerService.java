package com.example.hurtownia.domain.customer;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return customerRepository.save(mapper.mapToEntity(customerCreateRequest));
    }

    /**
     * Aktualizuje klienta.
     *
     * @param customerUpdateRequest aktualizowany klient
     */
    public Customer update(CustomerUpdateRequest customerUpdateRequest) {
        return customerRepository.save(mapper.mapToEntity(customerUpdateRequest));
    }
}
