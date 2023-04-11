package com.example.hurtownia.domain.customer;

import com.example.hurtownia.domain.order.OrderMapper;
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
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

    /**
     * Pobiera wszystkich klientów.
     *
     * @return lista wszystkich klientów
     */
    public List<CustomerTableViewDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(customer -> mapper.toDTO(customer))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera klienta o podanym id.
     *
     * @param id identyfikator klienta
     * @return klient
     */
    public Customer findById(Long id) {return customerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono klienta"));}

    /**
     * Usuwa klienta.
     *
     * @param customerTableViewDTO usuwany klient
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(CustomerTableViewDTO customerTableViewDTO) {
        try {
            customerRepository.delete(mapper.toEntity(customerTableViewDTO));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego klienta.
     *
     * @param customerCreateDTO nowy klient
     */
    public void save(CustomerCreateDTO customerCreateDTO) {
        customerRepository.save(mapper.toEntity(customerCreateDTO));
    }

    /**
     * Aktualizuje klienta.
     *
     * @param customerTableViewDTO aktualizowany klient
     */
    public void update(CustomerTableViewDTO customerTableViewDTO) {
        customerRepository.save(mapper.toEntity(customerTableViewDTO));
    }
}
