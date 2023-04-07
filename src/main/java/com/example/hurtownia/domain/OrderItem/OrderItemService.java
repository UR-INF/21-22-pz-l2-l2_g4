package com.example.hurtownia.domain.orderitem;

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
    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }

    /**
     * Pobiera elementy zamówienia o podanym id zamówienia.
     *
     * @param id identyfikator zamówienia
     * @return lista elementów zamówienia o podanym id zamówienia
     */
    public List<OrderItem> getOrderItems(Long id) {
        return orderItemRepository.findById(id).stream().toList();
    }

    /**
     * Usuwa element zamówienia.
     *
     * @param orderItem usuwany element zamówienia
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteOrderItem(OrderItem orderItem) {
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
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    /**
     * Aktualizuje element zamówienia.
     *
     * @param orderItem aktualizowany element zamówienia
     */
    public void updateOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

}
