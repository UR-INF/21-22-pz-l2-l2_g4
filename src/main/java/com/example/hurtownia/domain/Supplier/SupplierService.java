package com.example.hurtownia.domain.supplier;


import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zawiera metody dla tabeli 'dostawca'.
 */
@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierMapper mapper;

    /**
     * Pobiera wszystkich dostawców.
     *
     * @return lista wszystkich dostawców
     */
    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll().stream()
                .map(supplier -> mapper.mapToDto(supplier))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera dostawcę o podanym id.
     *
     * @param id identyfikator dostawcy
     * @return dostawca
     */
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono dostawcy"));
    }

    /**
     * Usuwa dostawcę.
     *
     * @param id identyfikator usuwanego dostawcy
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Long id) {
        try {
            supplierRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego dostawcę.
     *
     * @param supplierCreateRequest nowy dostawca
     */
    public Supplier save(SupplierCreateRequest supplierCreateRequest) {
        return supplierRepository.save(mapper.mapToEntity(supplierCreateRequest));
    }

    /**
     * Aktualizuje dostawcę.
     *
     * @param supplierUpdateRequest aktualizowany dostawca
     */
    public Supplier update(SupplierUpdateRequest supplierUpdateRequest) {
        return supplierRepository.save(mapper.mapToEntity(supplierUpdateRequest));
    }
}
