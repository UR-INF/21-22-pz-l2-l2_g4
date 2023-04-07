package com.example.hurtownia.domain.supplier;

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
    public List<Supplier> getSuppliers() {return supplierRepository.findAll();}

    /**
     * Pobiera dostawcę o podanym id.
     *
     * @param id identyfikator dostawcy
     * @return dostawca
     */
    public Supplier getSupplier(Long id) {
        return supplierRepository.findById(id).get();
    }

    /**
     * Usuwa dostawcę.
     *
     * @param supplier usuwany dostawca
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteSupplier(Supplier supplier) {
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
    public Supplier saveSupplier(Supplier supplier) {return supplierRepository.save(supplier);}

    /**
     * Aktualizuje dostawcę.
     *
     * @param supplier aktualizowany dostawca
     */
    public void updateSupplier(Supplier supplier) {supplierRepository.save(supplier);}

}
