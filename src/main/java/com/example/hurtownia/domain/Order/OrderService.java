package com.example.hurtownia.domain.order;

import org.hibernate.ObjectNotFoundException;
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
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Pobiera zamówienie o podanym id.
     *
     * @param id identyfikator zamówienia
     * @return zamówienie
     */
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Nie znaleziono zamówienia"));
    }

    /**
     * Usuwa zamówienie.
     *
     * @param order usuwane zamówienie
     * @return true - jeśli pomyślnie usunięto;
     * false - jeśli wystąpiły błędy
     */
    public boolean delete(Order order) {
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
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Aktualizuje zamówienie.
     *
     * @param newOrder aktualizowane zamówienie
     */
    public void update(Order newOrder) {
        Order order = findById(newOrder.getId());
        order.setCustomer(newOrder.getCustomer());
        order.setDate(newOrder.getDate());
        order.setState(newOrder.getState());
        order.setDiscount(newOrder.getDiscount());

        orderRepository.save(order);
    }
}
