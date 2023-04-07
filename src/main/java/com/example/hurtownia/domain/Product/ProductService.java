package com.example.hurtownia.domain.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'produkt'.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Pobiera wszystkie produkty.
     *
     * @return lista wszystkich produktów
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Pobiera produkt o podanym id.
     *
     * @param id identyfikator produktu
     * @return produkt
     */
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    /**
     * Usuwa produkt.
     *
     * @param product usuwany produkt
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteProduct(Product product) {
        try {
            productRepository.delete(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje produkt.
     *
     * @param product nowy produkt
     * @return dodany produkt
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Aktualizuje produkt.
     *
     * @param product aktualizowany produkt
     */
    public void updateProduct(Product product) {
        productRepository.save(product);
    }
}
