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
    public List<SupplierTableViewDTO> findAll() {
        return supplierRepository.findAll().stream()
                .map(supplier -> mapper.toDTO(supplier))
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
     * @param supplierTableViewDTO usuwany dostawca
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(SupplierTableViewDTO supplierTableViewDTO) {
        try {
            supplierRepository.delete(mapper.toEntity(supplierTableViewDTO));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowego dostawcę.
     *
     * @param supplierCreateDTO nowy dostawca
     */
    public void save(SupplierCreateDTO supplierCreateDTO) {
        supplierRepository.save(mapper.toEntity(supplierCreateDTO));
    }

    /**
     * Aktualizuje dostawcę.
     *
     * @param supplierTableViewDTO aktualizowany dostawca
     */
    public void update(SupplierTableViewDTO supplierTableViewDTO) {
        supplierRepository.save(mapper.toEntity(supplierTableViewDTO));
    }
}
