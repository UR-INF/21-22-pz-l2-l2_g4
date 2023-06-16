package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.product.request.ProductCreateRequest;
import com.example.hurtownia.domain.product.request.ProductUpdateRequest;
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
    private ProductMapper productMapper;

    /**
     * Pobiera wszystkie produkty.
     *
     * @return lista wszystkich produktów
     */
    public List<ProductDTO> findAll() {
        return productMapper.mapListToDto(productRepository.findAll());
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
    public Product create(ProductCreateRequest productCreateRequest) {
        Supplier supplier = supplierService.findById(productCreateRequest.getSupplierId());
        Product product = Product.builder()
                .supplier(supplier)
                .name(productCreateRequest.getName())
                .unitOfMeasurement(productCreateRequest.getUnitOfMeasurement())
                .price(productCreateRequest.getPrice())
                .country(productCreateRequest.getCountry())
                .code(productCreateRequest.getCode())
                .color(productCreateRequest.getColor())
                .number(productCreateRequest.getNumber())
                .maxNumber(productCreateRequest.getMaxNumber())
                .build();
        return productRepository.save(product);
    }

    /**
     * Aktualizuje produkt.
     *
     * @param productCreateRequest aktualizowany produkt
     */
    public Product update(ProductUpdateRequest productCreateRequest) {
        Supplier supplier = supplierService.findById(productCreateRequest.getSupplierId());
        Product product = findById(productCreateRequest.getId());
        product.setSupplier(supplier);
        product.setName(productCreateRequest.getName());
        product.setUnitOfMeasurement(productCreateRequest.getUnitOfMeasurement());
        product.setPrice(productCreateRequest.getPrice());
        product.setCountry(productCreateRequest.getCountry());
        product.setCode(productCreateRequest.getCode());
        product.setColor(productCreateRequest.getColor());
        product.setNumber(productCreateRequest.getNumber());
        product.setMaxNumber(productCreateRequest.getMaxNumber());
        if (productCreateRequest.getNumber() > productCreateRequest.getMaxNumber()) {
            product.setNumber(productCreateRequest.getMaxNumber());
        }
        if (productCreateRequest.getNumber() < 0) {
            product.setNumber(0);
        }
        return productRepository.save(product);
    }

    /**
     * Zwraca dane niezbedne do wygenerowania raportu dostawy.
     *
     * @param ids lista identyfikatorów produktów wybranych do dostawy
     * @return
     */
    public List<SupplyData> getSupplyData(List<Long> ids) {
        return ids.stream()
                .map(id -> {
                    Product product = findById(id);
                    Supplier supplier = supplierService.findById(product.getSupplier().getId());
                    return SupplyData.builder()
                            .supplierName(supplier.getName())
                            .productCode(product.getCode())
                            .productUnitOfMeasurement(product.getUnitOfMeasurement())
                            .amount(String.valueOf(product.getMaxNumber() - product.getNumber()))
                            .build();
                }).collect(Collectors.toList());
    }
}
