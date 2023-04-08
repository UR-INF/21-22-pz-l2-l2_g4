package com.example.hurtownia.domain.product;

import org.hibernate.ObjectNotFoundException;
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
    public List<Product> findAll() {
        return productRepository.findAll();
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
     * @param product usuwany produkt
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Product product) {
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
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Aktualizuje produkt.
     *
     * @param newProduct aktualizowany produkt
     */
    public void update(Product newProduct) {
        Product product = findById(newProduct.getId());
        product.setSupplier(newProduct.getSupplier());
        product.setName(newProduct.getName());
        product.setUnitOfMeasurement(newProduct.getUnitOfMeasurement());
        product.setPrice(newProduct.getPrice());
        product.setCountry(newProduct.getCountry());
        product.setCode(newProduct.getCode());
        product.setColor(newProduct.getColor());
        product.setNumber(newProduct.getNumber());
        product.setMaxNumber(newProduct.getMaxNumber());

        productRepository.save(product);
    }
}
