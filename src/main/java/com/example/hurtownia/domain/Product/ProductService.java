package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.supplier.Supplier;
import com.example.hurtownia.domain.supplier.SupplierService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zawiera metody dla tabeli 'produkt'.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ProductMapper mapper;

    /**
     * Pobiera wszystkie produkty.
     *
     * @return lista wszystkich produktów
     */
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(product -> mapper.mapToDto(product))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera produkt o podanym id.
     *
     * @param id identyfikator produktu
     * @return produkt
     */
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono produktu"));
    }

    /**
     * Usuwa produkt.
     *
     * @param id identyfikator usuwanego produktu
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Long id) {
        try {
            productRepository.delete(findById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje produkt.
     *
     * @param productCreateRequest nowy produkt
     */
    public Product save(ProductCreateRequest productCreateRequest) {
        Supplier supplier = supplierService.findById(productCreateRequest.getSupplierId());
        return productRepository.save(mapper.mapToEntity(productCreateRequest, supplier));
    }

    /**
     * Aktualizuje produkt.
     *
     * @param productCreateRequest aktualizowany produkt
     */
    public Product update(ProductUpdateRequest productCreateRequest) {
        Supplier supplier = supplierService.findById(productCreateRequest.getSupplierId());
        return productRepository.save(mapper.mapToEntity(productCreateRequest, supplier));
    }

    public List<SupplyData> getSupplyData(List<Long> ids) {
        return ids.stream()
                .map(id -> {
                    Product product = findById(id);
                    Supplier supplier = supplierService.findById(product.getSupplier().getId());
                    return mapper.mapToSupplyData(product, supplier);
                }).collect(Collectors.toList());
    }
}
