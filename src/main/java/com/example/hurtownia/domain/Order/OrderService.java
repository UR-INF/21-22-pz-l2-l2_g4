package com.example.hurtownia.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zawiera metody dla tabeli 'zamowienie'.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Pobiera wszystkie zamówienia.
     *
     * @return lista wszystkich zamówień
     */
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    /**
     * Pobiera zamówienie o podanym id.
     *
     * @param id identyfikator zamówienia
     * @return zamówienie
     */
    public Order getOrder(Long id) {
        return orderRepository.findById(id).get();
    }

    /**
     * Usuwa zamówienie.
     *
     * @param order usuwane zamówienie
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean deleteOrder(Order order) {
        try {
            orderRepository.delete(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dodaje nowe zamowienie.
     *
     * @param order nowe zzamówienie
     * @return dodane zamówienie
     */
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param order aktualizowane zamówienie
     */
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}
