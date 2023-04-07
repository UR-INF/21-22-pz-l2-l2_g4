package com.example.hurtownia.domain.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'klient'.
 */
@Service()
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Pobiera wszystkich klientów.
     *
     * @return lista wszystkich klientów
     */
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Pobiera klienta o podanym id.
     *
     * @param id identyfikator klienta
     * @return klient
     */
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).get();
    }

    /**
     * Usuwa klienta.
     *
     * @param customer usuwany klient
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteCustomer(Customer customer) {
        try {
            customerRepository.delete(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego klienta.
     *
     * @param customer nowy klient
     * @return dodany klient
     */
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Aktualizuje klienta.
     *
     * @param customer aktualizowany klient
     */
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
