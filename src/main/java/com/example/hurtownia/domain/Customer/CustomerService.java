package com.example.hurtownia.domain.customer;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'klient'.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Pobiera wszystkich klientów.
     *
     * @return lista wszystkich klientów
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
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
     * @param customer usuwany klient
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Customer customer) {
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
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Aktualizuje klienta.
     *
     * @param newCustomer aktualizowany klient
     */
    public void update(Customer newCustomer) {
        Customer customer = findById(newCustomer.getId());
        customer.setName(newCustomer.getName());
        customer.setSurname(newCustomer.getSurname());
        customer.setPesel(newCustomer.getPesel());
        customer.setPhoneNumber(newCustomer.getPhoneNumber());
        customer.setEmail(newCustomer.getEmail());
        customer.setPlace(newCustomer.getPlace());
        customer.setStreet(newCustomer.getStreet());
        customer.setBuildingNumber(newCustomer.getBuildingNumber());
        customer.setApartmentNumber(newCustomer.getApartmentNumber());

        customerRepository.save(customer);
    }
}
