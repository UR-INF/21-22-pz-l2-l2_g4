package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.customer.CustomerMapper;
import com.example.hurtownia.domain.order.OrderInvoiceDTO;
import com.example.hurtownia.domain.order.OrderTableViewDTO;
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
    private ProductMapper mapper;

    /**
     * Pobiera wszystkie produkty.
     *
     * @return lista wszystkich produktów
     */
    public List<ProductTableViewDTO> findAll() {
        return productRepository.findAll().stream()
                .map(product -> mapper.toDTO(product))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera produkt o podanym id.
     *
     * @param id identyfikator produktu
     * @return produkt
     */
    public Product findById(Long id) {return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono produktu"));}

    /**
     * Usuwa produkt.
     *
     * @param productTableViewDTO usuwany produkt
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(ProductTableViewDTO productTableViewDTO) {
        try {
            productRepository.delete(mapper.toEntity(productTableViewDTO));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje produkt.
     *
     * @param productCreateDTO nowy produkt
     */
    public void save(ProductCreateDTO productCreateDTO) {
        productRepository.save(mapper.toEntity(productCreateDTO));
    }

    /**
     * Aktualizuje produkt.
     *
     * @param productTableViewDTO aktualizowany produkt
     */
    public void update(ProductTableViewDTO productTableViewDTO) {
        productRepository.save(mapper.toEntity(productTableViewDTO));
    }

    public List<ProductSupplyDTO> getSupplyData(List<ProductTableViewDTO> productTableViewDTOs){
        return productTableViewDTOs.stream()
                .filter(productTableViewDTO -> productTableViewDTO.getSupply() == Boolean.TRUE)
                .map(productTableViewDTO -> mapper.toDTO(productTableViewDTO))
                .collect(Collectors.toList());
    }
}
