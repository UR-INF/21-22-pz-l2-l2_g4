package com.example.hurtownia.domain.supplier;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'dostawca'.
 */
@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * Pobiera wszystkich dostawców.
     *
     * @return lista wszystkich dostawców
     */
    public List<Supplier> findAll() {return supplierRepository.findAll();}

    /**
     * Pobiera dostawcę o podanym id.
     *
     * @param id identyfikator dostawcy
     * @return dostawca
     */
    public Supplier findById(Long id) {return supplierRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono dostawcy"));}

    /**
     * Usuwa dostawcę.
     *
     * @param supplier usuwany dostawca
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Supplier supplier) {
        try {
            supplierRepository.delete(supplier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego dostawcę.
     *
     * @param supplier nowy dostawca
     * @return dodany dostawca
     */
    public Supplier save(Supplier supplier) {return supplierRepository.save(supplier);}

    /**
     * Aktualizuje dostawcę.
     *
     * @param newSupplier aktualizowany dostawca
     */
    public void update(Supplier newSupplier) {
        Supplier supplier = findById(newSupplier.getId());
        supplier.setId(newSupplier.getId());
        supplier.setEmail(newSupplier.getEmail());
        supplier.setCountry(newSupplier.getCountry());
        supplier.setPlace(newSupplier.getPlace());
        supplier.setStreet(newSupplier.getStreet());
        supplier.setNip(newSupplier.getNip());

        supplierRepository.save(supplier);
    }
}
