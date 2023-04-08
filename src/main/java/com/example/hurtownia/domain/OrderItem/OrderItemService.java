package com.example.hurtownia.domain.orderitem;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'element_zamowienia'.
 */
@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    /**
     * Pobiera elementy zamówień.
     *
     * @return lista wszystkich elementów zamówień
     */
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    /**
     * Pobiera element zamówienia o podanym id.
     *
     * @param id identyfikator elementu zamówienia
     * @return element zamówienia
     */
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono elementu zamówienia"));
    }


    /**
     * Pobiera wszystkie elementy konkretnego zamówienia. Używana przy obliczaniu wartości całego zamówienia.
     *
     * @param id identyfikator zamówienia
     * @return lista elementów zamówienia o podanym id
     */
    public List<OrderItem> findByOrderId(Long id) {
        return orderItemRepository.findById(id).stream().toList();
    }

    /**
     * Usuwa element zamówienia.
     *
     * @param orderItem usuwany element zamówienia
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(OrderItem orderItem) {
        try {
            orderItemRepository.delete(orderItem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowy element zamówienia.
     *
     * @param orderItem nowy element zamówienia
     * @return dodany element zamówienia
     */
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    /**
     * Aktualizuje element zamówienia.
     *
     * @param newOrderItem aktualizowany element zamówienia
     */
    public void update(OrderItem newOrderItem) {
        OrderItem orderItem = findById(newOrderItem.getId());
        orderItem.setOrder(newOrderItem.getOrder());
        orderItem.setProduct(newOrderItem.getProduct());
        orderItem.setNumber(newOrderItem.getNumber());
        orderItem.setItemPrice(newOrderItem.getItemPrice());
        orderItem.setPricePerUnit(newOrderItem.getPricePerUnit());

        orderItemRepository.save(orderItem);
    }

}
